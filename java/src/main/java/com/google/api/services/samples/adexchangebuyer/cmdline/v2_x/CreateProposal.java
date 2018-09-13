/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.google.api.services.samples.adexchangebuyer.cmdline.v2_x;

import com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient;
import com.google.api.services.adexchangebuyer2.v2beta1.AdExchangeBuyerII;
import com.google.api.services.adexchangebuyer2.v2beta1.model.Buyer;
import com.google.api.services.adexchangebuyer2.v2beta1.model.Deal;
import com.google.api.services.adexchangebuyer2.v2beta1.model.DealTerms;
import com.google.api.services.adexchangebuyer2.v2beta1.model.GuaranteedFixedPriceTerms;
import com.google.api.services.adexchangebuyer2.v2beta1.model.Money;
import com.google.api.services.adexchangebuyer2.v2beta1.model.Price;
import com.google.api.services.adexchangebuyer2.v2beta1.model.PricePerBuyer;
import com.google.api.services.adexchangebuyer2.v2beta1.model.Proposal;
import com.google.api.services.adexchangebuyer2.v2beta1.model.Seller;
import com.google.api.services.samples.adexchangebuyer.cmdline.BaseSample;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This sample illustrates how to create a new proposal for a Programmatic Guaranteed Deal.
 */
public class CreateProposal extends BaseSample {
  @Override
  public ClientType getClientType() {
    return BaseSample.ClientType.ADEXCHANGEBUYERII;
  }

  @Override
  public String getName() {
    return "Create Marketplace Proposal";
  }

  @Override
  public String getDescription() {
    return "Creates a new proposal for a Programmatic Guaranteed Deal for the given buyer and "
        + "seller.";
  }

  @Override
  public void execute(AbstractGoogleJsonClient client) throws IOException {
    AdExchangeBuyerII adXClient = (AdExchangeBuyerII) client;
    String buyerAccountId = getStringInput("BuyerAccountId",
        "Enter the buyer's Account ID");
    String sellerAccountId = getStringInput("SellerAccountId",
        "Enter the seller's Account ID");
    String sellerSubAccountId = getStringInput("SellerSubAccountId",
        "Enter the seller's Sub-Account ID (if there is one)");

    // Build the Proposal for the proposals.create request.
    Buyer buyer = new Buyer();
    buyer.setAccountId(buyerAccountId);

    Seller seller = new Seller();
    seller.setAccountId(sellerAccountId);
    seller.setSubAccountId(sellerSubAccountId);

    Proposal proposal = new Proposal();
    proposal.setBuyer(buyer);
    proposal.setSeller(seller);
    proposal.setDisplayName(String.format("Test Proposal #%d",
        ThreadLocalRandom.current().nextInt(0, Integer.MAX_VALUE)));

    // Build the Programmatic Guaranteed Deal to be associated with the proposal.
    Money egsAmount = new Money();
    egsAmount.setCurrencyCode("USD");
    egsAmount.setUnits(0L);
    egsAmount.setNanos(1);

    Price estimatedGrossSpend = new Price();
    estimatedGrossSpend.setPricingType("COST_PER_MILLE");
    estimatedGrossSpend.setAmount(egsAmount);

    Money fixedPriceAmount = new Money();
    fixedPriceAmount.setCurrencyCode("USD");
    fixedPriceAmount.setUnits(0L);
    fixedPriceAmount.setNanos(1);

    Price fixedPrice = new Price();
    fixedPrice.setAmount(fixedPriceAmount);
    fixedPrice.setPricingType("COST_PER_MILLE");

    buyer = new Buyer();
    buyer.setAccountId(buyerAccountId);

    PricePerBuyer price = new PricePerBuyer();
    price.setPrice(fixedPrice);
    price.setBuyer(buyer);

    List<PricePerBuyer> fixedPrices = new ArrayList<>();
    fixedPrices.add(price);

    // The pricing terms used in this example are GuaranteedFixedPriceTerms, making this a
    // programmatic guaranteed deal. Alternatively, you could use NonGuaranteedFixedPriceTerms to
    // specify a preferred deal. Private auction deals use NonGuaranteedAuctionTerms; however, only
    // sellers can create this deal type.
    GuaranteedFixedPriceTerms guaranteedFixedPriceTerms = new GuaranteedFixedPriceTerms();
    guaranteedFixedPriceTerms.setFixedPrices(fixedPrices);
    guaranteedFixedPriceTerms.setGuaranteedLooks(1L);
    guaranteedFixedPriceTerms.setGuaranteedImpressions(1L);
    guaranteedFixedPriceTerms.setMinimumDailyLooks(1L);

    DealTerms dealTerms = new DealTerms();
    dealTerms.setEstimatedGrossSpend(estimatedGrossSpend);
    dealTerms.setGuaranteedFixedPriceTerms(guaranteedFixedPriceTerms);
    dealTerms.setDescription("Test deal.");
    dealTerms.setBrandingType("BRANDED");
    dealTerms.setEstimatedImpressionsPerDay(1L);
    dealTerms.setSellerTimeZone("America/New_York");

    Deal deal = new Deal();
    deal.setDealTerms(dealTerms);
    deal.setDisplayName(String.format("Test Deal #%d",
        ThreadLocalRandom.current().nextInt(0, Integer.MAX_VALUE)));
    deal.setSyndicationProduct("GAMES");

    List<Deal> deals = new ArrayList<>();
    deals.add(deal);

    // Append deals to the proposal.
    proposal.setDeals(deals);

    // Create the proposal.
    Proposal createdProposal = adXClient.accounts().proposals()
        .create(buyerAccountId, proposal).execute();

    System.out.println("========================================");
    System.out.printf("Created Proposal for Buyer Account ID \"%s\"%n",
        buyerAccountId);
    System.out.println("========================================");
    System.out.printf("\tProposal ID: %s%n",
        createdProposal.getProposalId());
    System.out.printf("\tBuyer Account ID: %s%n",
        createdProposal.getBuyer().getAccountId());
    System.out.printf("\tSeller Account ID: %s%n",
        createdProposal.getSeller().getAccountId());
    System.out.printf("\tSeller Sub-Account ID: %s%n",
        createdProposal.getSeller().getSubAccountId());
    System.out.printf("\tProposal State: %s%n",
        createdProposal.getProposalState());
    System.out.printf("\tProposal Revision: %d%n", createdProposal.getProposalRevision());
    for(Deal d : createdProposal.getDeals()) {
      System.out.printf("\t========================================%n");
      System.out.printf("\tDeal ID \"%s\"%n",
          d.getDealId());
      System.out.printf("\t========================================%n");
      System.out.printf("\t\tExternal Deal ID: %s%n", d.getExternalDealId());
      System.out.printf("\t\tDisplay Name: %s%n", d.getDisplayName());
      System.out.printf("\t\tExternal Deal ID: %s%n", d.getExternalDealId());
    }
  }
}
