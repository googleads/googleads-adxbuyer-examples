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
using System.Collections.Generic;

namespace Google.Apis.AdExchangeBuyer.Examples.v2_x
{
    /// <summary>
    /// This example accepts a Marketplace proposal.
    /// </summary>
    public class AcceptProposal : ExampleBase
    {
        /// <summary>
        /// Main method, to run this code example as a standalone application.
        /// </summary>
        /// <param name="args">The command line arguments</param>
        public static void Main(string[] args)
        {
            AdExchangeBuyerIIService service = Utilities.GetV2Service();
            ExampleBase example = new AcceptProposal();
            Console.WriteLine(example.Description);

            example.Run(service);
        }

        /// <summary>
        /// Returns a description about the code example.
        /// </summary>
        public override string Description
        {
            get { return "This code example accepts a proposal."; }
        }

        /// <summary>
        /// Runs the code example.
        /// </summary>
        /// <param name="service">An authenticated AdExchangeBuyerIIService</param>
        public override void Run(BaseClientService service)
        {
            AdExchangeBuyerIIService adXService = (AdExchangeBuyerIIService)service;

            string buyerAccountId = "INSERT_BUYER_ACCOUNT_ID_HERE";
            string proposalId = "INSERT_PROPOSAL_ID_HERE";
            long proposalRevision = long.Parse("INSERT_PROPOSAL_REVISION_HERE");

            AcceptProposalRequest acceptProposalBody = new AcceptProposalRequest();
            acceptProposalBody.ProposalRevision = proposalRevision;

            // Accept the Proposal
            Proposal acceptedProposal = adXService.Accounts.Proposals.Accept(
                acceptProposalBody, buyerAccountId, proposalId).Execute();

            Console.WriteLine("========================================");
            Console.WriteLine("Created new proposal for buyer account ID \"{0}\":",
                buyerAccountId);
            Console.WriteLine("========================================");
            Console.WriteLine("\tProposal ID: {0}", proposalId);
            Console.WriteLine("\tBuyer account ID: {0}", acceptedProposal.Buyer.AccountId);
            Console.WriteLine("\tSeller account ID: {0}", acceptedProposal.Seller.AccountId);
            Console.WriteLine("\tSeller sub-account ID: {0}", acceptedProposal.Seller.AccountId);
            Console.WriteLine("\tProposal state: {0}", acceptedProposal.ProposalState);
            Console.WriteLine("\tProposal revision: {0}", acceptedProposal.ProposalRevision);

            IList<Deal> proposalDeals = acceptedProposal.Deals;

            Console.WriteLine("\tDeals:");

            if (proposalDeals != null && proposalDeals.Count > 0)
            {
                foreach (Deal proposalDeal in proposalDeals)
                {
                    Console.WriteLine("\t\tDeal ID: {0}", proposalDeal.DealId);
                    Console.WriteLine("\t\t\tExternal deal ID: {0}",
                        proposalDeal.ExternalDealId);
                    Console.WriteLine("\t\t\tDisplay name: {0}", proposalDeal.DisplayName);
                }
            }
            else
            {
                Console.WriteLine("\t\tNone.");
            }
        }

        public override ClientType getClientType()
        {
            return ClientType.ADEXCHANGEBUYERII;
        }
    }
}