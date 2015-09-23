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

using System;

namespace Google.Apis.AdExchangeBuyer.Examples.v1_x
{
    /// <summary>
    /// Gets the status for a single creative. A creative will be available
    /// 30-40 minutes after submission.
    /// </summary>
    public class GetCreative : ExampleBase
    {
        /// <summary>
        /// Main method, to run this code example as a standalone application.
        /// </summary>
        /// <param name="args">The command line arguments</param>
        public static void Main(string[] args)
        {
            AdExchangeBuyerService service = Utilities.GetService();
            ExampleBase example = new GetCreative();
            Console.WriteLine(example.Description);
            example.Run(service);
        }

        /// <summary>
        /// Returns a description about the code example.
        /// </summary>
        public override string Description
        {
            get { return "This code example lists details on a specified creative."; }
        }

        /// <summary>
        /// Runs the code example.
        /// </summary>
        /// <param name="service">An authenticated AdExchangeBuyerService</param>
        public override void Run(AdExchangeBuyerService service)
        {
            int accountId = int.Parse("INSERT ACCOUNT ID HERE");
            String buyerCreativeId = "INSERT BUYER CREATIVE ID HERE";

            try
            {
                Creative creative = service.Creatives.Get(accountId, buyerCreativeId).Execute();
                Console.WriteLine("Account id: {0}", creative.AccountId);
                Console.WriteLine("Buyer Creative id: {0}", creative.BuyerCreativeId);
                Console.WriteLine("Deals Status: {0}", creative.DealsStatus);
                Console.WriteLine("Open Auction Status: {0}", creative.OpenAuctionStatus);
            }
            catch (Google.GoogleApiException)
            {
                Console.WriteLine("Can't find this creative.");
                Console.WriteLine("It can take up to 40 minutes after submitting a new creative"
                    + " for the status to be available.");
                Console.WriteLine("Check your input parameters");
                throw;
            }
        }
    }
}