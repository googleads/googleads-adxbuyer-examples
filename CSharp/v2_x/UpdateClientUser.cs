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
    /// Updates an existing client user for the given account ID and client
    /// buyer account ID.
    /// </summary>
    public class UpdateClientUser : ExampleBase
    {
        /// <summary>
        /// Main method, to run this code example as a standalone application.
        /// </summary>
        /// <param name="args">The command line arguments</param>
        public static void Main(string[] args)
        {
            AdExchangeBuyerIIService service = Utilities.GetV2Service();
            ExampleBase example = new UpdateClientUser();
            Console.WriteLine(example.Description);

            example.Run(service);
        }

        /// <summary>
        /// Returns a description about the code example.
        /// </summary>
        public override string Description
        {
            get { return "This code example updates an existing client user "
                    + "for a given account ID and client buyer account ID."; }
        }

        /// <summary>
        /// Runs the code example.
        /// </summary>
        /// <param name="service">An authenticated AdExchangeBuyerIIService</param>
        public override void Run(BaseClientService service)
        {
            AdExchangeBuyerIIService adXService =
                (AdExchangeBuyerIIService)service;

            long accountId = long.Parse("INSERT ACCOUNT ID HERE");
            long clientAccountId = long.Parse("INSERT CLIENT ACCOUNT ID HERE");
            long userId = long.Parse("INSERT CLIENT USER ID HERE");
            ClientUser clientUser = new ClientUser
            {
                // Valid values: "ACTIVE", or "DISABLED"
                Status = "INSERT STATUS HERE"
            };

            ClientUser responseClientUser = adXService.Accounts.Clients.Users
                .Update(clientUser, accountId, clientAccountId, userId).Execute();

            Console.WriteLine("Updated client user for account ID \"{0}\" and "
                + "client buyer ID \"{1}\":", accountId, clientAccountId);
            Console.WriteLine("User ID: {0}",
                responseClientUser.UserId);
            Console.WriteLine("Status: {0}",
                responseClientUser.Status);
            Console.WriteLine("Email: {0}",
                responseClientUser.Email);
        }

        public override ClientType getClientType()
        {
            return ClientType.ADEXCHANGEBUYERII;
        }
    }
}