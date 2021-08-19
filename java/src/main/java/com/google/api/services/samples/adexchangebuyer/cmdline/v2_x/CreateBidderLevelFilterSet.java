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
import com.google.api.services.adexchangebuyer2.v2beta1.model.FilterSet;
import com.google.api.services.adexchangebuyer2.v2beta1.model.Date;
import com.google.api.services.samples.adexchangebuyer.cmdline.BaseSample;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * This sample illustrates how to create a bidder-level filter set.
 *
 * A bidder-level filter set can be used to retrieve aggregated data for all Authorized Buyers
 * accounts under the given bidder account, including the bidder account itself.
 */
public class CreateBidderLevelFilterSet extends BaseSample {
  private DateTimeFormatter df = DateTimeFormat.forPattern("yyyyMMdd");

  @Override
  public ClientType getClientType() {
    return ClientType.ADEXCHANGEBUYERII;
  }

  @Override
  public String getName() {
    return "Create Bidder-level Filter Set";
  }

  @Override
  public String getDescription() {
    return "Creates a new Bidder-level Filter Set with the specified options.";
  }

  @Override
  public void execute(AbstractGoogleJsonClient client) throws IOException {
    AdExchangeBuyerII adXClient = (AdExchangeBuyerII) client;

    DateTime defaultEndTime = DateTime.now();
    DateTime defaultStartTime = defaultEndTime.minusDays(7);

    // Required fields.
    String bidderResourceId = getStringInput("bidderResourceId", "Enter the Bidder resource ID");
    String resourceId = getStringInput("resourceId", "Enter the Filter Set's desired resource ID");
    String startDate = getStringInput("startDate", "Enter the start date in YYYYMMDD format",
        df.print(defaultStartTime));
    String endDate = getStringInput("endDate", "Enter the end date in YYYYMMDD format",
        df.print(defaultEndTime));
    // Optional fields.
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

    String name = String.format("bidders/%s/filterSets/%s", bidderResourceId, resourceId);
    String ownerName = String.format("bidders/%s", bidderResourceId);

    FilterSet filterSet = new FilterSet();
    filterSet.setName(name);
    filterSet.setAbsoluteDateRange(buildAbsoluteDateRange(startDate, endDate));

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

    filterSet = adXClient.bidders().filterSets().create(ownerName, filterSet)
        .setIsTransient(isTransient).execute();

    System.out.println("========================================");
    System.out.printf("Filter Set created for Bidder with name \"%s\"%n", ownerName);
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
