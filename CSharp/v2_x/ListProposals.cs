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
    /// Retrieves a list of Marketplace proposals.
    /// </summary>
    public class ListProposals : ExampleBase
    {
        /// <summary>
        /// Main method, to run this code example as a standalone application.
        /// </summary>
        /// <param name="args">The command line arguments</param>
        public static void Main(string[] args)
        {
            AdExchangeBuyerIIService service = Utilities.GetV2Service();
            ExampleBase example = new ListProposals();
            Console.WriteLine(example.Description);

            example.Run(service);
        }

        /// <summary>
        /// Returns a description about the code example.
        /// </summary>
        public override string Description
        {
            get { return "This code example lists Marketplace proposals."; }
        }

        /// <summary>
        /// Runs the code example.
        /// </summary>
        /// <param name="service">An authenticated AdExchangeBuyerIIService</param>
        public override void Run(BaseClientService service)
        {
            AdExchangeBuyerIIService adXService = (AdExchangeBuyerIIService)service;
            string accountId = "INSERT ACCOUNT ID HERE";
            // Optional: Specify a filter to target proposals that match certain criteria.
            // string filter = "INSERT_FILTER_HERE";
            // Optional: Specify the filter syntax. The proposals.list method will use PQL by
            // default for legacy support, but it is recommended that new users use LIST_FILTER.
            // AccountsResource.ProposalsResource.ListRequest.FilterSyntaxEnum filterSyntax =
            //     AccountsResource.ProposalsResource.ListRequest.FilterSyntaxEnum.LISTFILTER;

            AccountsResource.ProposalsResource.ListRequest request = adXService.Accounts.Proposals
                .List(accountId);
            // request.Filter = filter;
            // request.FilterSyntax = filterSyntax;
            ListProposalsResponse response = request.Execute();

            Console.WriteLine("========================================\n");
            Console.WriteLine("Listing Marketplace publisher profiles for buyer account ID " +
                "\"{0}\"");
            Console.WriteLine("========================================\n");

            if (response.Proposals.Count == 0)
            {
                Console.WriteLine("No publisher profiles found.");
            } else
            {
                foreach (Proposal proposal in response.Proposals)
                {
                    Console.WriteLine("\tProposal ID: {0}", proposal.ProposalId);
                    Console.WriteLine("\tBuyer account ID: {0}", proposal.Buyer.AccountId);
                    Console.WriteLine("\tSeller account ID: {0}", proposal.Seller.AccountId);
                    Console.WriteLine("\tSeller sub-account ID: {0}", proposal.Seller.AccountId);
                    Console.WriteLine("\tProposal state: {0}", proposal.ProposalState);
                    Console.WriteLine("\tProposal revision: {0}", proposal.ProposalRevision);

                    IList<Deal> proposalDeals = proposal.Deals;

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
                    } else
                    {
                        Console.WriteLine("\t\tNone.");
                    }
                }
            }
        }

        public override ClientType getClientType()
        {
            return ClientType.ADEXCHANGEBUYERII;
        }
    }
}