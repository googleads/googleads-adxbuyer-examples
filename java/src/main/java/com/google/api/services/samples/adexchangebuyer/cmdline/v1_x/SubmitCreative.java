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

package com.google.api.services.samples.adexchangebuyer.cmdline.v1_x;

import com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient;
import com.google.api.services.adexchangebuyer.AdExchangeBuyer;
import com.google.api.services.adexchangebuyer.model.Creative;

import com.google.api.services.samples.adexchangebuyer.cmdline.BaseSample;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This sample illustrates how to submit a new creative to Google's verification pipeline.
 *
 * See the <a href="Creatives Guide">https://developers.google.com/authorized-buyers/apis/guides/v1/creatives</a>
 * for more details on the usage of this resource.
 */
public class SubmitCreative extends BaseSample {
  @Override
  public ClientType getClientType() {
    return BaseSample.ClientType.ADEXCHANGEBUYER;
  }

  @Override
  public String getName() {
    return "Submit Creative";
  }

  @Override
  public String getDescription() {
    return "Submits a new creative that will be reviewed by Google before it can serve.";
  }

  @Override
  public void execute(AbstractGoogleJsonClient client) throws IOException {
    AdExchangeBuyer adXClient = (AdExchangeBuyer) client;
    int accountId = getIntInput("AccountId", "Enter the creative account ID");
    String buyerCreativeId = getStringInput("BuyerCreativeId", "Enter the buyer creative ID");
    String advertiserName = getStringInput("AdvertiserName", "Enter the advertiser name");
    String clickThroughUrlsStr = getStringInput(
        "ClickThroughUrls", "Enter a comma separated list of clickthrough urls",
        "http://www.google.com");
    String htmlSnippet = getStringInput("HtmlSnippet", "Enter the Ad HTML code snippet",
        "<html><body><a href='http://www.google.com'>Hi there!</a></body></html>");
    long width = getLongInput("AdWidth", "Enter the creative width", 300L);
    long height = getLongInput("AdHeight", "Enter the creative height", 250L);
    Long agencyId = getOptionalLongInput("AgencyId", "Enter the agency id");

    Creative testCreative = new Creative();
    testCreative.setAccountId(accountId);
    testCreative.setAdvertiserName(advertiserName);
    testCreative.setBuyerCreativeId(buyerCreativeId);
    List<String> clickThroughUrls = new ArrayList<String>();
    Collections.addAll(clickThroughUrls, clickThroughUrlsStr.split("\\s*,\\s*"));
    testCreative.setClickThroughUrl(clickThroughUrls);
    testCreative.setHTMLSnippet(htmlSnippet);
    testCreative.setHeight((int) height);
    testCreative.setWidth((int) width);
    testCreative.setAgencyId(agencyId);

    Creative response = adXClient.creatives().insert(testCreative).execute();

    System.out.println("========================================");
    System.out.println("Submitted creative");
    System.out.println("========================================");
    System.out.println("Account ID: " + response.getAccountId());
    System.out.println("Buyer Creative ID: " + response.getBuyerCreativeId());
    System.out.println("Advertiser ID: " + response.getAdvertiserId());
    System.out.println("Agency id: " + response.getAgencyId());
    System.out.println("Open Auction Status: " + response.getOpenAuctionStatus());
    System.out.println("Deals Status: " + response.getDealsStatus());
    System.out.println("Product categories: " + response.getProductCategories());
    System.out.println("Sensitive categories: " + response.getSensitiveCategories());
    System.out.println("Width: " + response.getWidth());
    System.out.println("Height: " + response.getHeight());
    System.out.println("HTML Snippet: " + response.getHTMLSnippet());
  }
}
