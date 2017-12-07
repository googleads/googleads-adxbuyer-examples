/* Copyright 2017, Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

using Google.Apis.AdExchangeBuyerII.v2beta1;
using Google.Apis.AdExchangeBuyerII.v2beta1.Data;
using Google.Apis.Services;

using System;

namespace Google.Apis.AdExchangeBuyer.Examples.v2_x
{
    /// <summary>
    /// Retrieves account-level bid metrics for the given bidder, account, and filter set resource
    /// IDs.
    /// </summary>
    public class ListAccountLevelBidMetrics : ExampleBase
    {
        private static void PrintMetric(string metricName, MetricValue metric)
        {
            Console.WriteLine("\t{0}:", metricName);
            Console.WriteLine("\t\tValue: {0}",
                metric.Value != null ? metric.Value : 0);
            Console.WriteLine("\t\tVariance: {0}",
                metric.Variance != null ? metric.Variance : 0);
        }

        /// <summary>
        /// Main method, to run this code example as a standalone application.
        /// </summary>
        /// <param name="args">The command line arguments</param>
        public static void Main(string[] args)
        {
            AdExchangeBuyerIIService service = Utilities.GetV2Service();
            ExampleBase example = new ListAccountLevelBidMetrics();
            Console.WriteLine(example.Description);

            example.Run(service);
        }

        /// <summary>
        /// Returns a description about the code example.
        /// </summary>
        public override string Description
        {
            get { return "This code example lists account-level bid metrics for the given bidder, "
                    + "account, and filter set resource IDs."; }
        }

        /// <summary>
        /// Runs the code example.
        /// </summary>
        /// <param name="service">An authenticated AdExchangeBuyerIIService</param>
        public override void Run(BaseClientService service)
        {
            AdExchangeBuyerIIService adXService = (AdExchangeBuyerIIService)service;
            string bidderResourceId = "INSERT_BIDDER_RESOURCE_ID_HERE";
            string accountResourceId = "INSERT_ACCOUNT_RESOURCE_ID_HERE";
            string filterSetResourceId = "INSERT_FILTER_SET_RESOURCE_ID_HERE";
            string filterSetName = String.Format(
                "bidders/{0}/accounts/{1}/filterSets/{2}", bidderResourceId, accountResourceId,
                filterSetResourceId);

            ListBidMetricsResponse response = adXService.Bidders.Accounts.FilterSets.BidMetrics
                .List(filterSetName).Execute();

            Console.WriteLine("========================================\n");
            Console.WriteLine("Listing bid metrics for filter set \"{0}\"", filterSetName);
            Console.WriteLine("========================================\n");

            foreach (BidMetricsRow bidMetrics in response.BidMetricsRows)
            {
                TimeInterval timeInterval = bidMetrics.RowDimensions.TimeInterval;
                Console.WriteLine("* Bid Metrics from {0} - {1}:",
                    timeInterval.StartTime, timeInterval.EndTime);
                PrintMetric("Bids", bidMetrics.Bids);
                PrintMetric("BidsInAuction", bidMetrics.BidsInAuction);
                PrintMetric("BilledImpressions", bidMetrics.BilledImpressions);
                PrintMetric("ImpressionsWon", bidMetrics.ImpressionsWon);
                PrintMetric("MeasurableImpressions", bidMetrics.MeasurableImpressions);
                PrintMetric("ViewableImpressions", bidMetrics.ViewableImpressions);
            }
        }

        public override ClientType getClientType()
        {
            return ClientType.ADEXCHANGEBUYERII;
        }
    }
}