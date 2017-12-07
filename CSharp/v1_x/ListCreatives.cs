/* Copyright 2014, Google Inc. All Rights Reserved.
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

using Google.Apis.AdExchangeBuyer.v1_4;
using Google.Apis.AdExchangeBuyer.v1_4.Data;
using Google.Apis.Services;

using System;

namespace Google.Apis.AdExchangeBuyer.Examples.v1_x
{
    /// <summary>
    /// Retrieves a list of the authenticated user's active creatives. A creative will be available
    ///  30-40 minutes after submission.
    /// </summary>
    public class ListCreatives : ExampleBase
    {
        /// <summary>
        /// Main method, to run this code example as a standalone application.
        /// </summary>
        /// <param name="args">The command line arguments</param>
        public static void Main(string[] args)

        {
            AdExchangeBuyerService service = Utilities.GetV1Service();
            ExampleBase example = new ListCreatives();
            Console.WriteLine(example.Description);
            example.Run(service);
        }

        /// <summary>
        /// Returns a description about the code example.
        /// </summary>
        public override string Description
        {
            get { return "This code example lists all creatives."; }
        }

        /// <summary>
        /// Runs the code example.
        /// </summary>
        /// <param name="service">An authenticated AdExchangeBuyerService</param>
        public override void Run(BaseClientService service)
        {
            AdExchangeBuyerService adXService = (AdExchangeBuyerService)service;
            CreativesResource.ListRequest request = adXService.Creatives.List();

            // Maximum number of entries returned on one request to the API
            request.MaxResults = 100;
            CreativesList page = null;

            while (page == null || page.Items.Count == request.MaxResults)
            {
                if (page != null) request.PageToken = page.NextPageToken;

                page = request.Execute();

                foreach (Creative creative in page.Items)
                {
                    Console.WriteLine("Account id: {0}", creative.AccountId);
                    Console.WriteLine("Buyer Creative id: {0}", creative.BuyerCreativeId);
                    Console.WriteLine("Deals Status: {0}", creative.DealsStatus);
                    Console.WriteLine("Open Auction Status: {0}", creative.OpenAuctionStatus);
                    Console.WriteLine();
                }
            }
        }

        public override ClientType getClientType()
        {
            return ClientType.ADEXCHANGEBUYER;
        }
    }
}