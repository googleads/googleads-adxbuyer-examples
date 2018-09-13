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
    /// Retrieves the authenticated user's list of invitations for the given account ID and
    /// client buyer ID.
    /// </summary>
    public class ListInvitations : ExampleBase
    {
        /// <summary>
        /// Main method, to run this code example as a standalone application.
        /// </summary>
        /// <param name="args">The command line arguments</param>
        public static void Main(string[] args)
        {
            AdExchangeBuyerIIService service = Utilities.GetV2Service();
            ExampleBase example = new ListInvitations();
            Console.WriteLine(example.Description);

            example.Run(service);
        }

        /// <summary>
        /// Returns a description about the code example.
        /// </summary>
        public override string Description
        {
            get { return "This code example lists all invitations for a given account ID and " +
                    "client buyer ID."; }
        }

        /// <summary>
        /// Runs the code example.
        /// </summary>
        /// <param name="service">An authenticated AdExchangeBuyerIIService</param>
        public override void Run(BaseClientService service)
        {
            AdExchangeBuyerIIService adXService = (AdExchangeBuyerIIService)service;
            long accountId = long.Parse("INSERT ACCOUNT ID HERE");
            string clientBuyerId = "INSERT CLIENT BUYER ID HERE";

            ListClientUserInvitationsResponse response = adXService.Accounts
                .Clients.Invitations.List(accountId, clientBuyerId).Execute();

            Console.WriteLine("========================================\n");
            Console.WriteLine("Listing of invitations associated with Authorized Buyers account " +
                "\"{0}\" and client buyer ID \"{1}\"", accountId, clientBuyerId);
            Console.WriteLine("========================================\n");

            if (response.Invitations.Count == 0)
            {
                Console.WriteLine("No client users found.");
            } else
            {
                foreach (ClientUserInvitation invite in response.Invitations)
                {
                    Console.WriteLine("Invitations ID: {0}", invite.InvitationId);
                    Console.WriteLine("\tEmail: {0}", invite.Email);
                }
            }
        }

        public override ClientType getClientType()
        {
            return ClientType.ADEXCHANGEBUYERII;
        }
    }
}