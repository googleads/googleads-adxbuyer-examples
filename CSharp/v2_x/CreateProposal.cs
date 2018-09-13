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
    /// This example creates a new proposal for a programmatic guaranteed deal.
    /// </summary>
    public class CreateProposal : ExampleBase
    {
        /// <summary>
        /// Main method, to run this code example as a standalone application.
        /// </summary>
        /// <param name="args">The command line arguments</param>
        public static void Main(string[] args)
        {
            AdExchangeBuyerIIService service = Utilities.GetV2Service();
            ExampleBase example = new CreateProposal();
            Console.WriteLine(example.Description);

            example.Run(service);
        }

        /// <summary>
        /// Returns a description about the code example.
        /// </summary>
        public override string Description
        {
            get { return "This code example creates a new proposal with a programmatic " +
                    "guaranteed deal."; }
        }

        /// <summary>
        /// Runs the code example.
        /// </summary>
        /// <param name="service">An authenticated AdExchangeBuyerIIService</param>
        public override void Run(BaseClientService service)
        {
            AdExchangeBuyerIIService adXService = (AdExchangeBuyerIIService)service;

            string buyerAccountId = "INSERT_BUYER_ACCOUNT_ID_HERE";
            string sellerAccountId = "INSERT_SELLER_ACCOUNT_ID_HERE";
            // Optionally set seller sub-account ID.
            // string sellerSubAccountId = "INSERT_SELLER_SUB_ACCOUNT_ID_HERE";

            // Build the Proposal for the proposals.create request.
            Buyer buyer = new Buyer();
            buyer.AccountId = buyerAccountId;

            Seller seller = new Seller();
            seller.AccountId = sellerAccountId;
            // Optionally set seller sub-account ID.
            // seller.SubAccountId = sellerSubAccountId;

            Proposal proposal = new Proposal();
            proposal.Buyer = buyer;
            proposal.Seller = seller;
            proposal.DisplayName = string.Format("Test Proposal #{0}",
                Guid.NewGuid());

            // Build the Programmatic Guaranteed Deal to be associated with the proposal.
            Money egsAmount = new Money();
            egsAmount.CurrencyCode = "USD";
            egsAmount.Units = 0L;
            egsAmount.Nanos = 1;

            Price estimatedGrossSpend = new Price();
            estimatedGrossSpend.PricingType = "COST_PER_MILLE";
            estimatedGrossSpend.Amount = egsAmount;

            Money fixedPriceAmount = new Money();
            fixedPriceAmount.CurrencyCode = "USD";
            fixedPriceAmount.Units = 0L;
            fixedPriceAmount.Nanos = 1;

            Price fixedPrice = new Price();
            fixedPrice.PricingType = "COST_PER_MILLE";
            fixedPrice.Amount = fixedPriceAmount;

            PricePerBuyer price = new PricePerBuyer();
            price.Price = fixedPrice;
            price.Buyer = buyer;

            List<PricePerBuyer> fixedPrices = new List<PricePerBuyer>();
            fixedPrices.Add(price);

            // The pricing terms used in this example are GuaranteedFixedPriceTerms, make this a
            // programmatic guaranteed deal. Alternatively, you could use
            // NonGuaranteedFixPriceTerms to specify a preferred deal. Private auction deals use
            // NonGuaranteedAuctionTerms; however, only sellers can create this deal type.
            GuaranteedFixedPriceTerms guaranteedFixedPriceTerms = new GuaranteedFixedPriceTerms();
            guaranteedFixedPriceTerms.FixedPrices = fixedPrices;
            guaranteedFixedPriceTerms.GuaranteedLooks = 1;
            guaranteedFixedPriceTerms.GuaranteedImpressions = 1;
            guaranteedFixedPriceTerms.MinimumDailyLooks = 1;

            DealTerms dealTerms = new DealTerms();
            dealTerms.EstimatedGrossSpend = estimatedGrossSpend;
            dealTerms.GuaranteedFixedPriceTerms = guaranteedFixedPriceTerms;
            dealTerms.Description = "Test deal.";
            dealTerms.BrandingType = "BRANDED";
            dealTerms.EstimatedImpressionsPerDay = 1;
            dealTerms.SellerTimeZone = "America/New_York";

            Deal deal = new Deal();
            deal.DealTerms = dealTerms;
            deal.DisplayName = string.Format("Test Deal #{0}", Guid.NewGuid());
            deal.SyndicationProduct = "GAMES";

            List<Deal> deals = new List<Deal>();
            deals.Add(deal);

            // Append deals to the proposal.
            proposal.Deals = deals;

            // Create the Proposal
            AccountsResource.ProposalsResource.CreateRequest createRequest =
                adXService.Accounts.Proposals.Create(proposal, buyerAccountId);
            Proposal createdProposal = createRequest.Execute();
            string proposalId = createdProposal.ProposalId;

            Console.WriteLine("========================================");
            Console.WriteLine("Created new proposal for buyer account ID \"{0}\":",
                buyerAccountId);
            Console.WriteLine("========================================");
            Console.WriteLine("\tProposal ID: {0}", proposalId);
            Console.WriteLine("\tBuyer account ID: {0}", createdProposal.Buyer.AccountId);
            Console.WriteLine("\tSeller account ID: {0}", createdProposal.Seller.AccountId);
            Console.WriteLine("\tSeller sub-account ID: {0}", createdProposal.Seller.AccountId);
            Console.WriteLine("\tProposal state: {0}", createdProposal.ProposalState);
            Console.WriteLine("\tProposal revision: {0}", createdProposal.ProposalRevision);
            Console.WriteLine("\tDeals:");
            foreach (Deal proposalDeal in createdProposal.Deals)
            {
                Console.WriteLine("\t\tDeal ID: {0}", proposalDeal.DealId);
                Console.WriteLine("\t\t\tExternal deal ID: {0}", proposalDeal.ExternalDealId);
                Console.WriteLine("\t\t\tDisplay name: {0}", proposalDeal.DisplayName);
            }
        }

        public override ClientType getClientType()
        {
            return ClientType.ADEXCHANGEBUYERII;
        }
    }
}