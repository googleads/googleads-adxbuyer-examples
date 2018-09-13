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

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient;
import com.google.api.services.adexchangebuyer.AdExchangeBuyer;
import com.google.api.services.adexchangebuyer.model.Creative;
import com.google.api.services.adexchangebuyer.model.Creative.ServingRestrictions;
import com.google.api.services.adexchangebuyer.model.Creative.ServingRestrictions.DisapprovalReasons;

import com.google.api.services.samples.adexchangebuyer.cmdline.BaseSample;
import java.io.IOException;

/**
 * This sample illustrates how to retrieve a creative out of the system, including its status.
 *
 * See the <a href="Creatives Guide">https://developers.google.com/authorized-buyers/apis/guides/v1/creatives</a>
 * for more details on the usage of this resource.
 */
public class GetCreative extends BaseSample {
  @Override
  public ClientType getClientType() {
    return BaseSample.ClientType.ADEXCHANGEBUYER;
  }

  @Override
  public String getDescription() {
    return "Gets the data for a single creative, including its status";
  }

  @Override
  public String getName() {
    return "Get Creative Data";
  }

  @Override
  public void execute(AbstractGoogleJsonClient client) throws IOException {
    AdExchangeBuyer adXClient = (AdExchangeBuyer) client;
    int accountId = getIntInput("AccountId", "Enter the creative account id");
    String buyerCreativeId = getStringInput("BuyerCreativeId", "Enter the buyer creative id");

    try {
      Creative creative = adXClient.creatives().get(accountId, buyerCreativeId).execute();

      System.out.println("========================================");
      System.out.println("Found creative");
      System.out.println("========================================");
      System.out.println("Account id: " + creative.getAccountId());
      System.out.println("Buyer Creative id: " + creative.getBuyerCreativeId());
      System.out.println("Advertiser id: " + creative.getAdvertiserId());
      System.out.println("Agency id: " + creative.getAgencyId());
      System.out.println("Open Auction Status: " + creative.getOpenAuctionStatus());
      System.out.println("Deals Status: " + creative.getDealsStatus());
      if(creative.getServingRestrictions() != null) {
        for (ServingRestrictions servingRestriction : creative.getServingRestrictions()) {
          if (servingRestriction.getDisapprovalReasons() != null) {
            for (DisapprovalReasons disapprovalReason : servingRestriction.getDisapprovalReasons())
            {
              System.out.println("\tDisapproval Reason: " + disapprovalReason.getReason());
              for (String disapprovalReasonDetail : disapprovalReason.getDetails()) {
                System.out.println("\t\tDetail: " + disapprovalReasonDetail);
              }
            }
          }
        }
      }

      System.out.println("Product categories: " + creative.getProductCategories());
      System.out.println("Sensitive categories: " + creative.getSensitiveCategories());
      System.out.println("Width: " + creative.getWidth());
      System.out.println("Height: " + creative.getHeight());
      System.out.println("HTML Snippet: " + creative.getHTMLSnippet());
    } catch (GoogleJsonResponseException e) {
      if (e.getDetails().getCode() == 404) {
        System.out.println("Can't find this creative, it can take up to 20 minutes after "
            + "submitting a new creative for the status to be available. Check your input "
            + "parameters");
      } else {
        throw e;
      }
    }
  }
}
