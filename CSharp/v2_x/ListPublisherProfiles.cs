/* Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
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
    /// Retrieves all Marketplace publisher profiles.
    /// </summary>
    public class ListPublisherProfiles : ExampleBase
    {
        /// <summary>
        /// Main method, to run this code example as a standalone application.
        /// </summary>
        /// <param name="args">The command line arguments</param>
        public static void Main(string[] args)
        {
            AdExchangeBuyerIIService service = Utilities.GetV2Service();
            ExampleBase example = new ListPublisherProfiles();
            Console.WriteLine(example.Description);

            example.Run(service);
        }

        /// <summary>
        /// Returns a description about the code example.
        /// </summary>
        public override string Description
        {
            get { return "This code example lists all Marketplace publisher profiles."; }
        }

        /// <summary>
        /// Runs the code example.
        /// </summary>
        /// <param name="service">An authenticated AdExchangeBuyerIIService</param>
        public override void Run(BaseClientService service)
        {
            AdExchangeBuyerIIService adXService = (AdExchangeBuyerIIService)service;
            string accountId = "INSERT ACCOUNT ID HERE";

            ListPublisherProfilesResponse response = adXService.Accounts.PublisherProfiles
                .List(accountId).Execute();

            Console.WriteLine("========================================\n");
            Console.WriteLine("Listing Marketplace publisher profiles");
            Console.WriteLine("========================================\n");

            if (response.PublisherProfiles.Count == 0)
            {
                Console.WriteLine("No publisher profiles found.");
            } else
            {
                foreach (PublisherProfile publisherProfile in
                    response.PublisherProfiles)
                {
                    Console.WriteLine("Publisher profile ID: {0}",
                        publisherProfile.PublisherProfileId);
                    Console.WriteLine("\tSeller account ID: {0}",
                        publisherProfile.Seller.AccountId);
                    Console.WriteLine("\tSeller sub-account ID: {0}",
                        publisherProfile.Seller.SubAccountId);
                }
            }
        }

        public override ClientType getClientType()
        {
            return ClientType.ADEXCHANGEBUYERII;
        }
    }
}