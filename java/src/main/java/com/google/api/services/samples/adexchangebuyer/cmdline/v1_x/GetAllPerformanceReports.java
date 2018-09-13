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
import com.google.api.services.adexchangebuyer.model.PerformanceReport;

import com.google.api.services.samples.adexchangebuyer.cmdline.BaseSample;
import java.io.IOException;
import java.util.List;

/**
 * This sample illustrates how to retrieve all performance reports associated with the user.
 *
 *  * See the <a href="PerformanceReport Reference Documentation">https://developers.google.com/authorized-buyers/apis/v1/performanceReport/list</a>
 * for more details on the usage of this resource.
 */
public class GetAllPerformanceReports extends BaseSample {
  @Override
  public ClientType getClientType() {
    return BaseSample.ClientType.ADEXCHANGEBUYER;
  }

  @Override
  public String getName() {
    return "Get All Performance Reports";
  }

  @Override
  public String getDescription() {
    return "List performance reports associated with a given account ID";
  }

  @Override
  public void execute(AbstractGoogleJsonClient client) throws IOException {
    AdExchangeBuyer adXClient = (AdExchangeBuyer) client;
    long accountId = getIntInput("AccountId", "Enter the creative account ID");
    String startDate = getStringInput("endDateTime",
        "The end date of the report (older date) - mm/dd/yyyy format");
    String endDate = getStringInput("startDateTime",
        "The start date of the report (end date) - mm/dd/yyyy format");
    List<PerformanceReport> allReports = adXClient.
        performanceReport().
        list(accountId, endDate, startDate).
        execute().
        getPerformanceReport();

    if (allReports != null && allReports.size() > 0) {
      System.out.printf("========================================%n");
      System.out.printf("List of performance reports for account ID \"%s\"%n",
          accountId);
      System.out.printf("========================================%n");
      for (PerformanceReport report : allReports) {
        System.out.printf("Region: %s%n", report.getRegion());
        System.out.printf("\tTime Stamp: %s%n", report.getTimestamp());
        System.out.printf("\tPixel Match Requests: %s%n",
            report.getPixelMatchRequests());
        System.out.printf("\tPixel Match Responses: %s%n",
            report.getPixelMatchResponses());
      }
    } else {
      System.out.printf("No performance reports for account ID \"%s\"%n", accountId);
    }

  }
}
