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
    /// Inserts a new creative into Google's creative verification pipeline.
    /// </summary>
    public class InsertCreative : ExampleBase
    {
        /// <summary>
        /// Main method, to run this code example as a standalone application.
        /// </summary>
        /// <param name="args">The command line arguments</param>
        public static void Main(string[] args)
        {
            AdExchangeBuyerService service = Utilities.GetV1Service();
            ExampleBase example = new InsertCreative();
            Console.WriteLine(example.Description);
            example.Run(service);
        }

        /// <summary>
        /// Returns a description about the code example.
        /// </summary>
        public override string Description
        {
            get { return "This code example inserts a new creative."; }
        }

        /// <summary>
        /// Runs the code example.
        /// </summary>
        /// <param name="service">An authenticated AdExchangeBuyerService</param>
        public override void Run(BaseClientService service)
        {
            AdExchangeBuyerService adXService = (AdExchangeBuyerService)service;
            Creative creative = new Creative
            {
                AccountId = int.Parse("INSERT ACCOUNT ID HERE"),
                BuyerCreativeId = "INSERT BUYER CREATIVE ID HERE",
                AdvertiserName = "ADVERTISER NAME HERE",
                ClickThroughUrl = new[] { "CLICK THROUGH URL HERE" },
                HTMLSnippet = "<html><body><a href='URL HERE'>MESSAGE HERE!</a></body></html>",
                Width = 300,    // Width and Height need to change to accomodate the creative
                Height = 250
            };

            Creative responseCreative = adXService.Creatives.Insert(creative).Execute();

            Console.WriteLine("Inserted new creative:");
            Console.WriteLine("Account id: {0}", responseCreative.AccountId);
            Console.WriteLine("Buyer Creative id: {0}", responseCreative.BuyerCreativeId);
        }

        public override ClientType getClientType()
        {
            return ClientType.ADEXCHANGEBUYER;
        }
    }
}