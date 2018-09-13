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
import com.google.api.services.adexchangebuyer2.v2beta1.model.BidMetricsRow;
import com.google.api.services.adexchangebuyer2.v2beta1.model.ListBidMetricsResponse;
import com.google.api.services.adexchangebuyer2.v2beta1.model.MetricValue;
import com.google.api.services.adexchangebuyer2.v2beta1.model.TimeInterval;
import com.google.api.services.samples.adexchangebuyer.cmdline.BaseSample;
import java.io.IOException;
import java.util.List;

/**
 * This sample illustrates how to retrieve all Bidder-level Bid Metrics.
 */
public class GetBidderLevelBidMetrics extends BaseSample {
  @Override
  public ClientType getClientType() {
    return ClientType.ADEXCHANGEBUYERII;
  }

  @Override
  public String getName() {
    return "Get Bidder-level Bid Metrics.";
  }

  @Override
  public String getDescription() {
    return "Lists Bid Metrics for the specified Bidder using the given Filter Set.";
  }

  @Override
  public void execute(AbstractGoogleJsonClient client) throws IOException {
    AdExchangeBuyerII adXClient = (AdExchangeBuyerII) client;
    String bidderResourceId = getStringInput("bidderResourceId", "Enter the Bidder's resource ID");
    String filterSetResourceId = getStringInput("filterSetResourceId",
        "Enter the Filter Set's resource ID");
    String filterSetName = String.format("bidders/%s/filterSets/%s", bidderResourceId,
        filterSetResourceId);
    String pageToken = null;

    System.out.println("========================================");
    System.out.printf("Listing Bid Metrics using filterSet \"%s\"%n", filterSetName);
    System.out.println("========================================");

    do {
      ListBidMetricsResponse response = adXClient.bidders().filterSets().bidMetrics()
          .list(filterSetName).setPageToken(pageToken).execute();
      pageToken = response.getNextPageToken();
      List<BidMetricsRow> bidMetricsRows = response.getBidMetricsRows();

      for (BidMetricsRow bidMetricRow : bidMetricsRows) {
        TimeInterval timeInterval = bidMetricRow.getRowDimensions().getTimeInterval();
        MetricValue bids = bidMetricRow.getBids();
        MetricValue bidsInAuction = bidMetricRow.getBidsInAuction();
        MetricValue impressionsWon = bidMetricRow.getImpressionsWon();
        MetricValue billedImpressions = bidMetricRow.getBilledImpressions();
        MetricValue measurableImpressions = bidMetricRow.getMeasurableImpressions();
        MetricValue viewableImpressions = bidMetricRow.getViewableImpressions();

        System.out.printf("* Bid Metrics from %s - %s%n", timeInterval.getStartTime(),
            timeInterval.getEndTime());
        if(!bids.isEmpty()) {
          Long val = bids.getValue() != null ? bids.getValue() : 0;
          Long var = bids.getVariance() != null ? bids.getVariance() : 0;
          System.out.println("- Bids:");
          System.out.printf("\tValue: %s%n\tVariance: %s%n", val, var);
        }
        if(!bidsInAuction.isEmpty()) {
          Long val = bidsInAuction.getValue() != null ? bidsInAuction.getValue() : 0;
          Long var = bidsInAuction.getVariance() != null ? bidsInAuction.getVariance() : 0;
          System.out.println("- Bids in auction:");
          System.out.printf("\tValue: %s%n\tVariance: %s%n", val, var);
        }
        if(!impressionsWon.isEmpty()) {
          Long val = impressionsWon.getValue() != null ? impressionsWon.getValue() : 0;
          Long var = impressionsWon.getVariance() != null ? impressionsWon.getVariance() : 0;
          System.out.println("- Impressions won:");
          System.out.printf("\tValue: %s%n\tVariance: %s%n", val, var);
        }
        if(!billedImpressions.isEmpty()) {
          Long val = billedImpressions.getValue() != null ? billedImpressions.getValue() : 0;
          Long var = billedImpressions.getVariance() != null ? billedImpressions.getVariance() : 0;
          System.out.println("- Billed impressions:");
          System.out.printf("\tValue: %s%n\tVariance: %s%n", val, var);
        }
        if(!measurableImpressions.isEmpty()) {
          Long val = measurableImpressions.getValue() != null
              ? measurableImpressions.getValue() : 0;
          Long var = measurableImpressions.getVariance() != null
              ? measurableImpressions.getVariance() : 0;
          System.out.println("- Measurable impressions:");
          System.out.printf("\tValue: %s%n\tVariance: %s%n", val, var);
        }
        if(!viewableImpressions.isEmpty()) {
          Long val = viewableImpressions.getValue() != null ? viewableImpressions.getValue() : 0;
          Long var = viewableImpressions.getVariance() != null
              ? viewableImpressions.getVariance() : 0;
          System.out.println("- Viewable impressions: ");
          System.out.printf("\tValue: %s%n\tVariance: %s%n", val, var);
        }
      }
    } while(pageToken != null);
  }
}
