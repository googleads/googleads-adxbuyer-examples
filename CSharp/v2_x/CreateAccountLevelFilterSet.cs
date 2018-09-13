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
using System.Globalization;

namespace Google.Apis.AdExchangeBuyer.Examples.v2_x
{
    /// <summary>
    /// This example creates an account-level filter set.
    ///
    /// An account-level filter set can be used to retrieve data for a specific Authorized Buyers
    /// account, whether that be a bidder or child seat account.
    /// </summary>
    public class CreateAccountLevelFilterSet : ExampleBase
    {
        private static String dateFormat = "yyyyMMdd";

        private static AbsoluteDateRange BuildAbsoluteDateRange(String startDate, String endDate)
        {
            DateTime start_dt = DateTime.ParseExact(startDate, dateFormat,
                new CultureInfo("en-US"));
            DateTime end_dt = DateTime.ParseExact(endDate, dateFormat,
                new CultureInfo("en-US"));

            if (end_dt < start_dt)
            {
                throw new ArgumentException("Error: The end date can't be before the start date.");
            }

            Date start = new Date()
            {
                Year = start_dt.Year,
                Month = start_dt.Month,
                Day = start_dt.Day
            };

            Date end = new Date()
            {
                Year = end_dt.Year,
                Month = end_dt.Month,
                Day = end_dt.Day
            };

            AbsoluteDateRange dateRange = new AbsoluteDateRange()
            {
                StartDate = start,
                EndDate = end
            };

            return dateRange;
        }

        /// <summary>
        /// Main method, to run this code example as a standalone application.
        /// </summary>
        /// <param name="args">The command line arguments</param>
        public static void Main(string[] args)
        {
            AdExchangeBuyerIIService service = Utilities.GetV2Service();
            ExampleBase example = new CreateAccountLevelFilterSet();
            Console.WriteLine(example.Description);

            example.Run(service);
        }

        /// <summary>
        /// Returns a description about the code example.
        /// </summary>
        public override string Description
        {
            get { return "This code example creates a new account-level filter set for a given " +
                    "bidder and account resource ID."; }
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
            string resourceId = "INSERT_DESIRED_FILTER_SET_RESOURCE_ID_HERE";
            // Optional strings representing the start and end date for the AbsoluteDateRange to be
            // used in the new filter set. The date should be specified in the given format--e.g.
            // 20170101. If left unmodified, the dates for the first and last day of the past week
            // will be used.
            string startDateString = "YYYYMMDD";
            string endDateString = "YYYYMMDD";
            // By default, this example will create transient (temporary) filter sets. To create
            // filter sets that can be used indefinitely, set this to false.
            bool isTransient = true;

            string ownerName = string.Format("bidders/{0}/accounts/{1}", bidderResourceId,
                accountResourceId);

            if (startDateString.Equals("YYYYMMDD"))
            {
                if (endDateString.Equals("YYYYMMDD"))
                {
                    DateTime defaultEndDate = DateTime.Now;
                    endDateString = defaultEndDate.ToString(dateFormat);
                    DateTime defaultStartDate = defaultEndDate.AddDays(-7);
                    startDateString = defaultStartDate.ToString(dateFormat);
                } else
                {
                    throw new ArgumentException("Both the startDate and endDate must be set.");
                }
            }

            AbsoluteDateRange dateRange = BuildAbsoluteDateRange(startDateString, endDateString);

            FilterSet filterSet = new FilterSet
            {
                Name = string.Format("{0}/filterSets/{1}", ownerName, resourceId),
                AbsoluteDateRange = dateRange,
                CreativeId = "INSERT_CREATIVE_ID_HERE",
                DealId = long.Parse("INSERT_DEAL_ID_HERE"),
                // Valid values: DAILY, HOURLY
                TimeSeriesGranularity = "INSERT_TIME_SERIES_GRANULARITY_HERE",
                // Valid values: DISPLAY, VIDEO
                Formats = new List<string>() {"INSERT_FORMAT_HERE"},
                // Valid values: APP, WEB
                Environment = "INSERT_ENVIRONMENT_HERE",
                // Valid values: DESKTOP, MOBILE, TABLET
                Platforms = new List<string>() {"INSERT_PLATFORMS_HERE"},
                SellerNetworkIds = new List<int?>() {int.Parse("INSERT_SELLER_NETWORK_IDS_HERE")}
            };

            BiddersResource.AccountsResource.FilterSetsResource.CreateRequest request =
                adXService.Bidders.Accounts.FilterSets.Create(filterSet, ownerName);
            request.IsTransient = isTransient;
            FilterSet response = request.Execute();

            Console.WriteLine("Created new account-level filter set for owner \"{0}\":",
                ownerName);
            Console.WriteLine("Name: {0}", response.Name);
            Console.WriteLine("\tAbsoluteDateRange:");
            Date startDate = response.AbsoluteDateRange.StartDate;
            Console.WriteLine("\t\tStartDate:");
            Console.WriteLine("\t\t\tYear: {0}", startDate.Year);
            Console.WriteLine("\t\t\tMonth: {0}", startDate.Month);
            Console.WriteLine("\t\t\tDay: {0}", startDate.Day);
            Date endDate = response.AbsoluteDateRange.EndDate;
            Console.WriteLine("\t\tEndDate:");
            Console.WriteLine("\t\t\tYear: {0}", endDate.Year);
            Console.WriteLine("\t\t\tMonth: {0}", endDate.Month);
            Console.WriteLine("\t\t\tDay: {0}", endDate.Day);

            string creativeId = response.CreativeId;
            if (creativeId != null)
            {
                Console.WriteLine("\tCreativeId: {0}", creativeId);
            }
            long? dealId = response.DealId;
            if (dealId != null)
            {
                Console.WriteLine("\tDealId: {0}", dealId);
            }
            String timeSeriesGranularity = response.TimeSeriesGranularity;
            if (timeSeriesGranularity != null)
            {
                Console.WriteLine("\tTimeSeriesGranularity: {0}", timeSeriesGranularity);
            }
            IList<String> formats = response.Formats;
            if (formats != null)
            {
                Console.WriteLine("\tFormats:");
                foreach (string format in formats)
                {
                    Console.WriteLine("\t\t{0}", format);
                }
            }
            String environment = response.Environment;
            if (environment != null)
            {
                Console.WriteLine("\tEnvironment: {0}", environment);
            }
            IList<string> platforms = response.Platforms;
            if (platforms != null)
            {
                Console.WriteLine("\tPlatforms:");
                foreach (string platform in platforms)
                {
                    Console.WriteLine("\t\t{0}", platform);
                }
            }
            IList<int?> sellerNetworkIds = response.SellerNetworkIds;
            if (sellerNetworkIds != null)
            {
                Console.WriteLine("\tSellerNetworkIds:");
                foreach (int? sellerNetworkId in sellerNetworkIds)
                {
                    Console.WriteLine("\t\t{0}", sellerNetworkId);
                }
            }
        }

        public override ClientType getClientType()
        {
            return ClientType.ADEXCHANGEBUYERII;
        }
    }
}