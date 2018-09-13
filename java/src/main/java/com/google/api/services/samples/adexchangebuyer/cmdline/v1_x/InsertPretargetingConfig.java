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
import com.google.api.services.adexchangebuyer.model.PretargetingConfig;
import com.google.api.services.adexchangebuyer.model.PretargetingConfig.Dimensions;

import com.google.api.services.samples.adexchangebuyer.cmdline.BaseSample;
import java.io.IOException;
import java.util.Arrays;

/**
 * This sample illustrates how to insert a pretargetingConfig.
 *
 * See the <a href="Pretargeting Guide">https://developers.google.com/authorized-buyers/apis/guides/v1/pretargeting</a>
 * for more details on the usage of this resource.
 */
public class InsertPretargetingConfig extends BaseSample {
  @Override
  public ClientType getClientType() {
    return BaseSample.ClientType.ADEXCHANGEBUYER;
  }

  @Override
  public String getName() {
    return "Insert PretargetingConfig";
  }

  @Override
  public String getDescription() {
    return "Inserts a new pretargeting config";
  }

  @Override
  public void execute(AbstractGoogleJsonClient client) throws IOException {
    AdExchangeBuyer adXClient = (AdExchangeBuyer) client;
    long accountId = getLongInput("AccountId", "Enter the account id");
    String configName = getStringInput("configName",
        "Enter the name for the pretargetConfig");
    long width = getLongInput("AdWidth", "Enter the width to target", 300L);
    long height = getLongInput("AdHeight", "Enter the height to target", 250L);

    PretargetingConfig newConfig = new PretargetingConfig();
    newConfig.setConfigName(configName);
    newConfig.setIsActive(true);
    newConfig.setCreativeType(Arrays.asList("PRETARGETING_CREATIVE_TYPE_HTML"));

    Dimensions targetDimensions = new Dimensions();
    targetDimensions.setWidth(width);
    targetDimensions.setHeight(height);
    newConfig.setDimensions(Arrays.asList(targetDimensions));

    PretargetingConfig config = adXClient.pretargetingConfig().
        insert(accountId, newConfig).execute();

    System.out.println("========================================");
    System.out.println("Inserted pretargeting config");
    System.out.println("========================================");
    System.out.println("Config Name: " + config.getConfigName());
    System.out.println("Config Id: " + config.getConfigId());
    System.out.println("Is Active: " + config.getIsActive());
    System.out.println("CreativeType: " + config.getCreativeType());
    System.out.println("Dimensions: ");
    for (Dimensions d : config.getDimensions()) {
      System.out.printf("\r%s\t%s%n", d.getWidth(), d.getHeight());
    }
  }
}
