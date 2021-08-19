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
import com.google.api.services.adexchangebuyer2.v2beta1.model.Date;
import com.google.api.services.adexchangebuyer2.v2beta1.model.FilterSet;
import com.google.api.services.samples.adexchangebuyer.cmdline.BaseSample;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * This sample illustrates how to create an account-level filter set.
 *
 * An account-level filter set can be used to retrieve data for a specific Authorized Buyers
 * account, whether that be a bidder or child seat account.
 */
public class CreateAccountLevelFilterSet extends BaseSample {
  private DateTimeFormatter df = DateTimeFormat.forPattern("yyyyMMdd");

  @Override
  public ClientType getClientType() {
    return ClientType.ADEXCHANGEBUYERII;
  }

  @Override
  public String getName() {
    return "Create Account-level Filter Set";
  }

  @Override
  public String getDescription() {
    return "Creates a new Account-level Filter Set with the specified options.";
  }

  @Override
  public void execute(AbstractGoogleJsonClient client) throws IOException {
    AdExchangeBuyerII adXClient = (AdExchangeBuyerII) client;

    DateTime defaultEndTime = DateTime.now();
    DateTime defaultStartTime = defaultEndTime.minusDays(7);

    // Required fields.
    String bidderResourceId = getStringInput("bidderResourceId", "Enter the Bidder resource ID");
    String accountResourceId = getStringInput("accountResourceId", "Enter the Account resource ID");
    String resourceId = getStringInput("resourceId", "Enter the Filter Set's desired resource ID");
    String startDate = getStringInput("startDate", "Enter the start date in YYYYMMDD format",
        df.print(defaultStartTime));
    String endDate = getStringInput("endDate", "Enter the end date in YYYYMMDD format",
        df.print(defaultEndTime));
    // Optional fields.
    String creativeId = getOptionalStringInput("creativeId",
        "Enter the creativeId on which to filter");
    Long dealId = getOptionalLongInput("dealId", "Enter the dealId on which to filter.");
    String environment = getOptionalStringInput("environment",
        "Enter the environment on which to filter");
    String format = getOptionalStringInput("format", "Enter the Format on which to filter");
    List<String> platforms = getOptionalStringListInput("platforms",
        "Enter a comma separated list of Platforms on which to filter");
    List<Integer> sellerNetworkIds = getOptionalIntListInput("sellerNetworkIds",
        "Enter a comma separated list of integer seller network IDs on which to filter");
    String timeSeriesGranularity = getOptionalStringInput("timeSeriesGranularity",
        "Enter the TimeSeriesGranularity representing the desired granularity of time intervals "
            + "(if a time series breakdown is desired)");
    boolean isTransient = getBooleanInput("isTransient",
        "Enter a boolean representing whether the Filter Set is transient", true);

    String name = String.format("bidders/%s/accounts/%s/filterSets/%s", bidderResourceId,
        accountResourceId, resourceId);
    String ownerName = String.format("bidders/%s/accounts/%s", bidderResourceId, accountResourceId);

    FilterSet filterSet = new FilterSet();
    filterSet.setName(name);
    filterSet.setAbsoluteDateRange(buildAbsoluteDateRange(startDate, endDate));

    if(creativeId != null) {
      filterSet.setCreativeId(creativeId);
    }
    if(dealId != null) {
      filterSet.setDealId(dealId);
    }
    if(environment != null) {
      filterSet.setEnvironment(environment);
    }
    if(format != null) {
      filterSet.setFormat(format);
    }
    if(platforms != null) {
      filterSet.setPlatforms(platforms);
    }
    if(sellerNetworkIds != null) {
      filterSet.setSellerNetworkIds(sellerNetworkIds);
    }
    if(timeSeriesGranularity != null) {
      filterSet.setTimeSeriesGranularity(timeSeriesGranularity);
    }

    filterSet = adXClient.bidders().accounts().filterSets().create(ownerName, filterSet)
        .setIsTransient(isTransient).execute();

    System.out.println("========================================");
    System.out.printf("Filter Set created for Account with name \"%s\"%n", ownerName);
    System.out.println("========================================");
    System.out.printf("Filter Set name: %s%n", filterSet.getName());
    AbsoluteDateRange absDateRange = filterSet.getAbsoluteDateRange();
    System.out.println("AbsoluteDateRange");
    System.out.printf("\tStart date: %s%n",
        convertDateToString(absDateRange.getStartDate()));
    System.out.printf("\tEnd date: %s%n",
        convertDateToString(absDateRange.getEndDate()));
    timeSeriesGranularity = filterSet.getTimeSeriesGranularity();
    if(timeSeriesGranularity != null) {
      System.out.printf("Time series granularity: %s%n", timeSeriesGranularity);
    }
    creativeId = filterSet.getCreativeId();
    if(creativeId != null) {
      System.out.printf("Creative ID: %s%n", creativeId);
    }
    dealId = filterSet.getDealId();
    if(dealId != null) {
      System.out.printf("Deal ID: %s%n", dealId);
    }
    format = filterSet.getFormat();
    if(format != null) {
        System.out.printf("\tFormat: %s%n", format);
    }
    environment = filterSet.getEnvironment();
    if(environment != null) {
      System.out.printf("Environment: %s%n", environment);
    }
    platforms = filterSet.getPlatforms();
    if(platforms != null) {
      System.out.println("Platforms:");
      for(String platform : platforms) {
        System.out.printf("\t%s%n", platform);
      }
    }
    sellerNetworkIds = filterSet.getSellerNetworkIds();
    if(filterSet.getSellerNetworkIds() != null) {
      System.out.println("Seller network IDS:");
      for(Integer sellerNetworkId : sellerNetworkIds) {
        System.out.printf("\t%d%n", sellerNetworkId);
      }
    }
  }

  private AbsoluteDateRange buildAbsoluteDateRange(String startDate, String endDate) {
    DateTime s = df.parseDateTime(startDate);
    DateTime e = df.parseDateTime(endDate);

    if(e.isBefore(s.getMillis())) {
      throw new IllegalArgumentException("Error: The end date can not be before the start date.");
    }

    Date start = new Date();
    start.setYear(s.getYear());
    start.setMonth(s.getMonthOfYear());
    start.setDay(s.getDayOfMonth());
    Date end = new Date();
    end.setYear(e.getYear());
    end.setMonth(e.getMonthOfYear());
    end.setDay(e.getDayOfMonth());

    AbsoluteDateRange absoluteDateRange = new AbsoluteDateRange();
    absoluteDateRange.setStartDate(start);
    absoluteDateRange.setEndDate(end);

    return absoluteDateRange;
  }

  private String convertDateToString(Date date) {
    return String.format("%d%02d%02d", date.getYear(), date.getMonth(), date.getDay());
  }
}
