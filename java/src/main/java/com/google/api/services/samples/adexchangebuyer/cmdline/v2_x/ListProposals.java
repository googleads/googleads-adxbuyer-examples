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
import com.google.api.services.adexchangebuyer2.v2beta1.model.Proposal;
import com.google.api.services.samples.adexchangebuyer.cmdline.BaseSample;
import java.io.IOException;
import java.util.List;

/**
 * This sample illustrates how to list Marketplace proposals.
 */
public class ListProposals extends BaseSample {
  @Override
  public ClientType getClientType() {
    return BaseSample.ClientType.ADEXCHANGEBUYERII;
  }

  @Override
  public String getName() {
    return "List Marketplace Proposals.";
  }

  @Override
  public String getDescription() {
    return "Lists Marketplace Proposals based on the given filter.";
  }

  @Override
  public void execute(AbstractGoogleJsonClient client) throws IOException {
    AdExchangeBuyerII adXClient = (AdExchangeBuyerII) client;
    String accountId = Long.toString(getIntInput("AccountId", "Enter the Account ID"));
    String filter = getStringInput("Filter", "Enter the filter", "");
    String filterSyntax = getStringInput("FilterSyntax", "Enter the filter syntax", "LIST_FILTER");

    List<Proposal> proposals = adXClient.accounts().proposals().list(accountId)
        .setFilter(filter).setFilterSyntax(filterSyntax).execute().getProposals();

    if (proposals != null && proposals.size() > 0) {
      System.out.println("========================================");
      System.out.printf("Listing of Proposals for filter \"%s\"%n", filter);
      System.out.println("========================================");
      for (Proposal proposal : proposals) {
        System.out.printf("Proposal ID: %s%n", proposal.getProposalId());
        System.out.printf("\tBuyer Account ID: %s%n",
            proposal.getBuyer().getAccountId());
        System.out.printf("\tSeller Account ID: %s%n",
            proposal.getSeller().getAccountId());
        System.out.printf("\tSeller Sub-Account ID: %s%n",
            proposal.getSeller().getSubAccountId());
        System.out.printf("\tProposal State: %s%n",
            proposal.getProposalState());
        System.out.printf("\tProposal Revision: %s%n",
            proposal.getProposalRevision());
      }
    } else {
      System.out.println("No Proposals were found for the given filter.");
    }
  }
}
