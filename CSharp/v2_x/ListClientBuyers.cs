/* Copyright 2016, Google Inc. All Rights Reserved.
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
    /// Retrieves the authenticated user's list of client buyers for the given account ID.
    /// </summary>
    public class ListClientBuyers : ExampleBase
    {
        /// <summary>
        /// Main method, to run this code example as a standalone application.
        /// </summary>
        /// <param name="args">The command line arguments</param>
        public static void Main(string[] args)
        {
            AdExchangeBuyerIIService service = Utilities.GetV2Service();
            ExampleBase example = new ListClientBuyers();
            Console.WriteLine(example.Description);

            example.Run(service);
        }

        /// <summary>
        /// Returns a description about the code example.
        /// </summary>
        public override string Description
        {
            get { return "This code example lists all client buyers for a given account ID."; }
        }

        /// <summary>
        /// Runs the code example.
        /// </summary>
        /// <param name="service">An authenticated AdExchangeBuyerIIService</param>
        public override void Run(BaseClientService service)
        {
            AdExchangeBuyerIIService adXService = (AdExchangeBuyerIIService)service;
            long accountId = long.Parse("INSERT ACCOUNT ID HERE");

            ListClientsResponse response = adXService.Accounts.Clients.List(accountId).Execute();

            Console.WriteLine("========================================\n");
            Console.WriteLine("Listing of client buyers associated with Authorized Buyers " +
                "Account \"{0}\"", accountId);
            Console.WriteLine("========================================\n");

            if (response.Clients.Count == 0)
            {
                Console.WriteLine("No client buyers found.");
            } else
            {
                foreach (Client clientBuyer in response.Clients)
                {
                    Console.WriteLine("Client Account ID: {0}", clientBuyer.ClientAccountId);
                    Console.WriteLine("\tClient Name: {0}", clientBuyer.ClientName);
                    Console.WriteLine("\tEntity ID: {0}", clientBuyer.EntityId);
                    Console.WriteLine("\tEntity Name: {0}", clientBuyer.EntityName);
                    Console.WriteLine("\tEntity Type: {0}", clientBuyer.EntityType);
                    Console.WriteLine("\tRole: {0}", clientBuyer.Role);
                    Console.WriteLine("\tStatus: {0}", clientBuyer.Status);
                }
            }
        }

        public override ClientType getClientType()
        {
            return ClientType.ADEXCHANGEBUYERII;
        }
    }
}