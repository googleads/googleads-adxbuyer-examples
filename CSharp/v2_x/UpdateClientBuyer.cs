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
    /// Updates an existing client buyer for the given account ID.
    /// </summary>
    public class UpdateClientBuyer : ExampleBase
    {
        /// <summary>
        /// Main method, to run this code example as a standalone application.
        /// </summary>
        /// <param name="args">The command line arguments</param>
        public static void Main(string[] args)
        {
            AdExchangeBuyerIIService service = Utilities.GetV2Service();
            ExampleBase example = new UpdateClientBuyer();
            Console.WriteLine(example.Description);

            example.Run(service);
        }

        /// <summary>
        /// Returns a description about the code example.
        /// </summary>
        public override string Description
        {
            get { return "This code example updates an existing client buyer for a given " +
                    "account ID."; }
        }

        /// <summary>
        /// Runs the code example.
        /// </summary>
        /// <param name="service">An authenticated AdExchangeBuyerIIService</param>
        public override void Run(BaseClientService service)
        {
            AdExchangeBuyerIIService adXService = (AdExchangeBuyerIIService)service;

            long accountId = long.Parse("INSERT ACCOUNT ID HERE");
            long clientAccountId = long.Parse("INSERT CLIENT ACCOUNT ID HERE");
            Client clientBuyer = new Client
            {
                ClientName = "INSERT CLIENT NAME HERE",
                EntityId = long.Parse("INSERT ENTITY ID HERE"),
                EntityName = "INSERT ENTITY NAME HERE",
                // Valid values: "ADVERTISER", "BRAND", or "AGENCY"
                EntityType = "INSERT ENTITY TYPE HERE",
                // Valid values: "CLIENT_DEAL_VIEWER",
                // "CLIENT_DEAL_NEGOTIATOR" or "CLIENT_DEAL_APPROVER"
                Role = "INSERT ROLE HERE",
                // Valid values: "ACTIVE", or "DISABLED"
                Status = "INSERT STATUS HERE",
                VisibleToSeller = bool.Parse("INSERT VISIBLE_TO_SELLER HERE")
            };

            Client responseClientBuyer = adXService.Accounts.Clients.Update(
                clientBuyer, accountId, clientAccountId).Execute();

            Console.WriteLine("Updated client buyer for account ID \"{0}\":", accountId);
            Console.WriteLine("Client account ID: {0}", responseClientBuyer.ClientAccountId);
            Console.WriteLine("Client name: {0}", responseClientBuyer.ClientName);
            Console.WriteLine("Entity ID: {0}", responseClientBuyer.EntityId);
            Console.WriteLine("Entity name: {0}", responseClientBuyer.EntityName);
            Console.WriteLine("Entity type: {0}", responseClientBuyer.EntityType);
            Console.WriteLine("Role: {0}", responseClientBuyer.Role);
            Console.WriteLine("Status: {0}", responseClientBuyer.Status);
            Console.WriteLine("Visible to seller: {0}", responseClientBuyer.VisibleToSeller);
        }

        public override ClientType getClientType()
        {
            return ClientType.ADEXCHANGEBUYERII;
        }
    }
}