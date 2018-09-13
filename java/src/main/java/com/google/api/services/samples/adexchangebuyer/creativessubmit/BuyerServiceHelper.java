/*
 * Copyright (c) 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.google.api.services.samples.adexchangebuyer.creativessubmit;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.IOUtils;
import com.google.api.services.adexchangebuyer.AdExchangeBuyer;
import com.google.api.services.adexchangebuyer.AdExchangeBuyerRequest;
import com.google.api.services.adexchangebuyer.AdExchangeBuyerScopes;
import com.google.api.services.adexchangebuyer.model.Creative;
import com.google.api.services.adexchangebuyer.model.PretargetingConfig;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.GeneralSecurityException;

/**
 * Wraps calls to the Authorized Buyers Ad Exchange Buyer API.
 */
public class BuyerServiceHelper {
  /**
   * Be sure to specify the name of your application. Suggested format is
   * "MyCompany-ProductName/1.0".
   */
  protected static final String APPLICATION_NAME = "";

  /** Full path to JSON Key file - include file name */
  private static final java.io.File JSON_FILE =
      new java.io.File("INSERT_PATH_TO_JSON_FILE");

  /**
   * Fields in the Creatives object that must not be sent on insert/update/patch requests
   */
  protected static final String[] CREATIVE_READONLY_FIELDS = {"advertiserId",
      "corrections",
      "disapprovalReasons",
      "filteringReasons",
      "productCategories",
      "sensitiveCategories",
  "status"};

  /**
   * Fields in the PretargetingConfig object that must not be sent on insert/update/patch requests
   */
  protected static final String[] PRETARGETING_CONFIG_READONLY_FIELDS = {"billingId", "configId"};

  /**
   * Handle to the API service
   */
  private AdExchangeBuyer service;

  /**
   * Handler user input and output
   */
  private BuyerServiceInputUtils inputHelper;

  /**
   * Template used to produce the application name specified in requests made by this tool.
   */
  private static final String APPLICATION_NAME_TEMPLATE= "%s (creativessubmit)";

  /**
   * @return the API service
   */
  protected AdExchangeBuyer getService() {
    return service;
  }

  public BuyerServiceHelper(BuyerServiceInputUtils inputHelper) throws GeneralSecurityException,
  IOException {
    super();
    this.inputHelper = inputHelper;
    this.service = getNewService();
  }


  /**
   * Retrieves the authenticated user's list of accounts.
   */
  public void accountsList() {
    try {
      run(getService().accounts().list());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Retrieves a list of the authenticated user's active creatives.
   */
  public void creativesList() {
    try {
      String filter = inputHelper.getCreativeListFilter();
      run(getService().creatives().list().setOpenAuctionStatusFilter(filter)
          .setDealsStatusFilter(filter));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Gets the status for a single creative.
   */
  public void creativesGet() {
    try {
      long accountId = inputHelper.getAccountId();
      String creativeId = inputHelper.getCreativeId();
      String filePath = inputHelper.getCreativeFilePath();
      run(getService().creatives().get((int) accountId, creativeId), true, filePath);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Inserts a new creative into Google's creative verification pipeline.
   */
  public void creativesInsert() {
    try {
      String filePath = inputHelper.getCreativeFilePath();
      Creative creative = readObjectFromFile(filePath, Creative.class);
      BuyerServiceHelper.cleanCreative(creative);
      run(getService().creatives().insert(creative));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Retrieves a list of the pretargeting configurations for the passed accountId.
   */
  public void pretargetingConfigList() {
    try {
      long accountId = inputHelper.getAccountId();
      run(getService().pretargetingConfig().list(accountId));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Gets a specific pretargeting configuration.
   */
  public void pretargetingConfigGet() {
    try {
      long accountId = inputHelper.getAccountId();
      long configId = inputHelper.getPretargetingConfigId();
      String filePath = inputHelper.getPretargetingFilePath();
      run(getService().pretargetingConfig().get(accountId, configId), true, filePath);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Inserts a new pretargeting configuration.
   */
  public void pretargetingConfigInsert() {
    try {
      long accountId = inputHelper.getAccountId();
      String filePath = inputHelper.getPretargetingFilePath();
      PretargetingConfig config = readObjectFromFile(filePath, PretargetingConfig.class);
      BuyerServiceHelper.cleanPretargetingConfig(config);
      run(getService().pretargetingConfig().insert(accountId, config));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Updates an existing pretargeting config.
   * Note: The contents of the json file will replace any existing data; not be merged with it.
   */
  public void pretargetingConfigUpdate() {
    try {
      long accountId = inputHelper.getAccountId();
      long configId = inputHelper.getPretargetingConfigId();
      String filePath = inputHelper.getPretargetingFilePath();
      PretargetingConfig config = readObjectFromFile(filePath, PretargetingConfig.class);
      BuyerServiceHelper.cleanPretargetingConfig(config);
      run(getService().pretargetingConfig().update(accountId, configId, config));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Runs a request against the service
   * @param request is the service method to run
   * @return json results from request
   * @throws IOException
   */
  protected String run(AdExchangeBuyerRequest<?> request) throws IOException {
    return run(request, true, "");
  }

  /**
   * Runs a request against the service
   * @param request is the service method to run
   * @param display displays returned results when true
   * @param filePath returned results are saved to this path
   * @return json results from request
   * @throws IOException
   */
  protected String run(AdExchangeBuyerRequest<?> request, boolean display, String filePath)
      throws IOException {
    HttpResponse response = request.executeUnparsed();
    String json = getResultAsString(response);
    if (display) {
      System.out.println(json);
    }
    if (!filePath.isEmpty()) {
      Files.write(Paths.get(filePath), json.getBytes(), StandardOpenOption.CREATE);
    }
    return json;
  }

  /**
   * reads results from the response object and converts them to a String
   * @param response you want to read content from
   * @return the response content as a String
   * @throws IOException
   */
  protected static String getResultAsString(HttpResponse response) throws IOException {
    InputStream content = response.getContent();
    if (content == null) {
      return "";
    }
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    IOUtils.copy(content, out);
    String json = out.toString(response.getContentCharset().name());
    return json;
  }

  /**
   * Reads serialized data from a file to recreate an object
   * @param filePath the file to read
   * @param dataClass the object type
   * @return a instantiated object of type dataClass
   * @throws IOException
   */
  protected <T> T readObjectFromFile(String filePath, Class<T> dataClass) throws IOException {
    String json = new String(Files.readAllBytes(Paths.get(filePath)));
    StringReader reader = new StringReader(json);
    return service.getObjectParser().parseAndClose(reader, dataClass);
  }

  /**
   * Sets fields from CREATIVE_READONLY_FIELDS as null
   * @param creative is the Creative to clean
   */
  protected static void cleanCreative(Creative creative) {
    for (String field : CREATIVE_READONLY_FIELDS) {
      creative.set(field, null);
    }
  }

  /**
   * Sets fields from PRETARGETING_CONFIG_READONLY_FIELDS as null
   * @param config is the PretargetingConfig to clean
   */
  protected static void cleanPretargetingConfig(PretargetingConfig config) {
    for (String field : PRETARGETING_CONFIG_READONLY_FIELDS) {
      config.set(field, null);
    }
  }

  /**
   * Creates a new API service
   * @return the new, authenticated and authorized service
   * @throws GeneralSecurityException
   * @throws IOException
   */
  protected static AdExchangeBuyer getNewService() throws GeneralSecurityException, IOException {
    /** Global instance of the HTTP transport. */
    HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

    /** Global instance of the JSON factory. */
    JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

    Credential credential = GoogleCredential.fromStream(new FileInputStream(JSON_FILE))
        .createScoped(AdExchangeBuyerScopes.all());

    String applicationName = String.format(APPLICATION_NAME_TEMPLATE, APPLICATION_NAME);

    return new AdExchangeBuyer.Builder(httpTransport, jsonFactory, credential).setApplicationName(
        applicationName).build();
  }
}
