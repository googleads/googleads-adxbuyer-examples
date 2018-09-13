/*
 * Copyright (c) 2017 Google Inc.
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

package com.google.api.services.samples.adexchangebuyer.cmdline.v2_x;

import com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient;
import com.google.api.services.adexchangebuyer2.v2beta1.AdExchangeBuyerII;
import com.google.api.services.adexchangebuyer2.v2beta1.model.AbsoluteDateRange;
import com.google.api.services.adexchangebuyer2.v2beta1.model.Client;
import com.google.api.services.adexchangebuyer2.v2beta1.model.Date;
import com.google.api.services.adexchangebuyer2.v2beta1.model.FilterSet;
import com.google.api.services.adexchangebuyer2.v2beta1.model.RealtimeTimeRange;
import com.google.api.services.adexchangebuyer2.v2beta1.model.RelativeDateRange;
import com.google.api.services.samples.adexchangebuyer.cmdline.BaseSample;
import java.io.IOException;
import java.util.List;

/**
 * This sample illustrates how to retrieve all Bidder-level Filter Sets.
 */
public class GetAllBidderLevelFilterSets extends BaseSample {
  @Override
  public ClientType getClientType() {
    return ClientType.ADEXCHANGEBUYERII;
  }

  @Override
  public String getName() {
    return "Get All Bidder-level Filter Sets.";
  }

  @Override
  public String getDescription() {
    return "Lists Filter Sets associated with the given Bidder.";
  }

  @Override
  public void execute(AbstractGoogleJsonClient client) throws IOException {
    AdExchangeBuyerII adXClient = (AdExchangeBuyerII) client;
    String bidderResourceId = getStringInput("bidderResourceId", "Enter the Bidder's resource ID");
    String ownerName = String.format("bidders/%s", bidderResourceId);
    List<FilterSet> allFilterSets = adXClient.bidders().filterSets().list(ownerName).execute()
        .getFilterSets();

    if (allFilterSets != null && allFilterSets.size() > 0) {
      System.out.println("========================================");
      System.out.printf("Listing of Filter Sets associated with Bidder \"%s\"%n", ownerName);
      System.out.println("========================================");
      for (FilterSet filterSet : allFilterSets) {
        System.out.printf("* Filter Set name: %s%n", filterSet.getName());
        AbsoluteDateRange absDateRange = filterSet.getAbsoluteDateRange();
        if(absDateRange != null) {
          System.out.println("AbsoluteDateRange");
          System.out.printf("\tStart date: %s%n",
              convertDateToString(absDateRange.getStartDate()));
          System.out.printf("\tEnd date: %s%n",
              convertDateToString(absDateRange.getEndDate()));
        }
        RelativeDateRange relDateRange = filterSet.getRelativeDateRange();
        if(relDateRange != null) {
          Integer offset = relDateRange.getOffsetDays();
          System.out.println("RelativeDateRange");
          System.out.printf("\tOffset days: %s%n", offset != null ? offset : 0);
          System.out.printf("\tDuration days: %s%n", relDateRange.getDurationDays());
        }
        RealtimeTimeRange rtTimeRange = filterSet.getRealtimeTimeRange();
        if(rtTimeRange != null) {
          System.out.println("RealtimeTimeRange");
          System.out.printf("\tStart timestamp: %s%n", rtTimeRange.getStartTimestamp());
        }
        String timeSeriesGranularity = filterSet.getTimeSeriesGranularity();
        if(timeSeriesGranularity != null) {
          System.out.printf("Time series granularity: %s%n", timeSeriesGranularity);
        }
        List<String> formats = filterSet.getFormats();
        if(formats != null) {
          System.out.println("Formats:");
          for(String format : formats) {
            System.out.printf("\tFormat: %s%n", format);
          }
        }
        String environment = filterSet.getEnvironment();
        if(environment != null) {
          System.out.printf("Environment: %s%n", environment);
        }
        List<String> platforms = filterSet.getPlatforms();
        if(platforms != null) {
          System.out.println("Platforms:");
          for(String platform : platforms) {
            System.out.printf("\t%s%n", platform);
          }
        }
        List<Integer> sellerNetworkIds = filterSet.getSellerNetworkIds();
        if(filterSet.getSellerNetworkIds() != null) {
          System.out.println("Seller network IDS:");
          for(Integer sellerNetworkId : sellerNetworkIds) {
            System.out.printf("\t%d%n", sellerNetworkId);
          }
        }
      }
    } else {
      System.out.printf("No Filter Sets were found associated with Bidder \"%s\"%n", ownerName);
    }
  }

  private String convertDateToString(Date date) {
    return String.format("%d%02d%02d", date.getYear(), date.getMonth(), date.getDay());
  }
}
