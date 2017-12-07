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
    /// Creates a new invitation for the given account ID and client account ID.
    /// </summary>
    public class CreateInvitation : ExampleBase
    {
        /// <summary>
        /// Main method, to run this code example as a standalone application.
        /// </summary>
        /// <param name="args">The command line arguments</param>
        public static void Main(string[] args)
        {
            AdExchangeBuyerIIService service = Utilities.GetV2Service();
            ExampleBase example = new CreateInvitation();
            Console.WriteLine(example.Description);

            example.Run(service);
        }

        /// <summary>
        /// Returns a description about the code example.
        /// </summary>
        public override string Description
        {
            get { return "This code example creates a new invitation for a given account ID and " +
                    "client account ID."; }
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

            ClientUserInvitation invite = new ClientUserInvitation
            {
                Email = "INSERT EMAIL ADDRESS HERE"
            };

            ClientUserInvitation responseInvite = adXService.Accounts.Clients.Invitations.Create(
                invite, accountId, clientAccountId).Execute();

            Console.WriteLine("Created and sent new invitation for account ID \"{0}\" and client " +
                "account ID \"{1}\" to \"{2}\".", accountId, responseInvite.ClientAccountId,
                invite.Email);
        }

        public override ClientType getClientType()
        {
            return ClientType.ADEXCHANGEBUYERII;
        }
    }
}