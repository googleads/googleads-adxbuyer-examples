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
using System.Collections.Generic;

namespace Google.Apis.AdExchangeBuyer.Examples.v2_x
{
    /// <summary>
    /// Retrieves the account-level filter sets for the given bidder and account resource IDs.
    /// </summary>
    public class ListAccountLevelFilterSets : ExampleBase
    {
        /// <summary>
        /// Main method, to run this code example as a standalone application.
        /// </summary>
        /// <param name="args">The command line arguments</param>
        public static void Main(string[] args)
        {
            AdExchangeBuyerIIService service = Utilities.GetV2Service();
            ExampleBase example = new ListAccountLevelFilterSets();
            Console.WriteLine(example.Description);

            example.Run(service);
        }

        /// <summary>
        /// Returns a description about the code example.
        /// </summary>
        public override string Description
        {
            get { return "This code example lists all account-level filter sets for the given " +
                    "bidder and account resource IDs."; }
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
            string ownerName = String.Format("bidders/{0}/accounts/{1}", bidderResourceId,
                accountResourceId);

            ListFilterSetsResponse response = adXService.Bidders.Accounts.FilterSets.List(
                ownerName).Execute();

            Console.WriteLine("========================================\n");
            Console.WriteLine("Listing of filter sets associated with owner name \"{0}\"",
                ownerName);
            Console.WriteLine("========================================\n");

            if (response.FilterSets.Count == 0)
            {
                Console.WriteLine("No filter sets found.");
            } else
            {
                foreach (FilterSet filterSet in response.FilterSets)
                {
                    Console.WriteLine("* Name: {0}", filterSet.Name);
                    AbsoluteDateRange absDateRange = filterSet.AbsoluteDateRange;
                    if (absDateRange != null)
                    {
                        Console.WriteLine("\tAbsoluteDateRange:");
                        Date startDate = absDateRange.StartDate;
                        Console.WriteLine("\t\tStartDate:");
                        Console.WriteLine("\t\t\tYear: {0}", startDate.Year);
                        Console.WriteLine("\t\t\tMonth: {0}", startDate.Month);
                        Console.WriteLine("\t\t\tDay: {0}", startDate.Day);
                        Date endDate = absDateRange.EndDate;
                        Console.WriteLine("\t\tEndDate:");
                        Console.WriteLine("\t\t\tYear: {0}", endDate.Year);
                        Console.WriteLine("\t\t\tMonth: {0}", endDate.Month);
                        Console.WriteLine("\t\t\tDay: {0}", endDate.Day);
                    }
                    RelativeDateRange relDateRange = filterSet.RelativeDateRange;
                    if (relDateRange != null)
                    {
                        Console.WriteLine("\tRelativeDateRange:");
                        Console.WriteLine("\t\tOffsetDays: {0}", relDateRange.OffsetDays);
                        Console.WriteLine("\t\tDurationDays: {0}", relDateRange.DurationDays);
                    }
                    RealtimeTimeRange rtTimeRange = filterSet.RealtimeTimeRange;
                    if (rtTimeRange != null)
                    {
                        Console.WriteLine("\tRealtimeTimeRange:");
                        Console.WriteLine("\t\tStartTimestamp: {0}", rtTimeRange.StartTimestamp);
                    }
                    string creativeId = filterSet.CreativeId;
                    if (creativeId != null)
                    {
                        Console.WriteLine("\tCreativeId: {0}", creativeId);
                    }
                    long? dealId = filterSet.DealId;
                    if (dealId != null)
                    {
                        Console.WriteLine("\tDealId: {0}", dealId);
                    }
                    String timeSeriesGranularity = filterSet.TimeSeriesGranularity;
                    if (timeSeriesGranularity != null)
                    {
                        Console.WriteLine("\tTimeSeriesGranularity: {0}", timeSeriesGranularity);
                    }
                    IList<String> formats = filterSet.Formats;
                    if (formats != null)
                    {
                        Console.WriteLine("\tFormats:");
                        foreach (string format in formats)
                        {
                            Console.WriteLine("\t\t{0}", format);
                        }
                    }
                    String environment = filterSet.Environment;
                    if (environment != null)
                    {
                        Console.WriteLine("\tEnvironment: {0}", environment);
                    }
                    IList<string> platforms = filterSet.Platforms;
                    if (platforms != null)
                    {
                        Console.WriteLine("\tPlatforms:");
                        foreach (string platform in platforms)
                        {
                            Console.WriteLine("\t\t{0}", platform);
                        }
                    }
                    IList<int?> sellerNetworkIds = filterSet.SellerNetworkIds;
                    if (sellerNetworkIds != null)
                    {
                        Console.WriteLine("\tSellerNetworkIds:");
                        foreach (int? sellerNetworkId in sellerNetworkIds)
                        {
                            Console.WriteLine("\t\t{0}", sellerNetworkId);
                        }
                    }
                }
            }
        }

        public override ClientType getClientType()
        {
            return ClientType.ADEXCHANGEBUYERII;
        }
    }
}