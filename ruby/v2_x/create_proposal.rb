#!/usr/bin/env ruby
# Encoding: utf-8
#
# Copyright:: Copyright 2018 Google LLC
#
# License:: Licensed under the Apache License, Version 2.0 (the "License");
#           you may not use this file except in compliance with the License.
#           You may obtain a copy of the License at
#
#           https://www.apache.org/licenses/LICENSE-2.0
#
#           Unless required by applicable law or agreed to in writing, software
#           distributed under the License is distributed on an "AS IS" BASIS,
#           WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
#           implied. See the License for the specific language governing
#           permissions and limitations under the License.
#
# This example illustrates how to create a proposal.
#
# Tags: Accounts.Proposals.create

require 'optparse'

require_relative '../samples_util'


def create_proposal(ad_exchange_buyer, account_id, proposal)
  begin
    created_proposal = ad_exchange_buyer.create_account_proposal(
      account_id, proposal
    )

    proposal_id = created_proposal.proposal_id
    puts 'Created new proposal for buyer account ID %s:' % account_id
    puts "\tProposal ID: %s" % proposal_id
    puts "\tBuyer account ID: %s" % created_proposal.buyer.account_id
    puts "\tSeller account ID: %s" % created_proposal.seller.account_id
    puts "\tSeller sub-account ID: %s" % created_proposal.seller.sub_account_id
    puts "\tProposal state: %s" % created_proposal.proposal_state
    puts "\tProposal revision: %d" % created_proposal.proposal_revision
    puts "\tDeals:"
    created_proposal.deals.each do |deal|
      puts "\t\tDeal ID: %s" % deal.deal_id
      puts "\t\t\tExternal deal ID: %s" % deal.external_deal_id
      puts "\t\t\tDisplay name: %s" % deal.display_name
    end
  rescue Google::Apis::ServerError => e
    raise "The following server error occured:\n%s" % e.message
  rescue Google::Apis::ClientError => e
    raise "Invalid client request:\n%s" % e.message
  rescue Google::Apis::AuthorizationError => e
    raise "Authorization error occured:\n%s" % e.message
  end
end


if __FILE__ == $0
  begin
    # Retrieve the service used to make API requests.
    service = get_service(ADEXCHANGEBUYER_V2BETA1)
  rescue ArgumentError => e
    raise 'Unable to create service, with error message: %s' % e.message
  rescue Signet::AuthorizationError => e
    raise ('Unable to create service, was the KEY_FILE in samples_util.rb ' +
           'set? Error message: %s') % e.message
  end

  # Set options and default values for fields used in this example.
  options = [
    Option.new(
      'account_id', 'The integer ID of the Authorized Buyers account.',
      :type => String, :short_alias => 'a', :required => true,
      :default_value => nil  # Insert default value here.
    ),
    Option.new(
      'seller_account_id',
      ('The integer accountId of the seller for which you are making the '
       'proposal.'),
      :type => String, :short_alias => 's', :required => true,
      :default_value => nil  # Insert default value here.
    ),
    Option.new(
      'seller_sub_account_id',
      ('The optional integer subAccountId of the seller for which you are '
       'making the proposal.'),
      :required => false,
      :default_value => nil  # Insert default value here.
    )
  ]

  # Parse options.
  parser = Parser.new(options)
  opts = parser.parse(ARGV)

  # Build the proposal for the proposals.create request.
  new_proposal = Google::Apis::Adexchangebuyer2V2beta1::Proposal.new(
      buyer: Google::Apis::Adexchangebuyer2V2beta1::Buyer.new(
          account_id: opts['account_id']),
      seller: Google::Apis::Adexchangebuyer2V2beta1::Seller.new(
          account_id: opts['seller_account_id'],
          sub_account_id: opts['seller_sub_account_id']),
      display_name:'Test Proposal #%d' % rand(10000000..100000000))

  # Build the deal that will be appended to the proposal in the
  # proposals.update request.
  estimated_gross_spend = Google::Apis::Adexchangebuyer2V2beta1::Price.new(
      pricing_type: 'COST_PER_MILLE',
      amount: Google::Apis::Adexchangebuyer2V2beta1::Money.new(
          currency_code: 'USD', units: 0, nanos: 1))

  fixed_price = Google::Apis::Adexchangebuyer2V2beta1::PricePerBuyer.new(
      buyer: Google::Apis::Adexchangebuyer2V2beta1::Buyer.new(
          account_id: opts['account_id']),
      price: Google::Apis::Adexchangebuyer2V2beta1::Price.new(
          pricing_type: 'COST_PER_MILLE',
          amount: Google::Apis::Adexchangebuyer2V2beta1::Money.new(
              currency_code: 'USD', units: 0, nanos: 1)))

  # The pricing terms used in this example are guaranteed fixed price terms,
  # making this a programmatic guaranteed deal. Alternatively, you could use
  # non-guaranteed fixed price terms to specify a preferred deal. Private
  # auction deals use non-guaranteed auction terms; however, only sellers can
  # create this deal type.
  guaranteed_fixed_price_terms = (
      Google::Apis::Adexchangebuyer2V2beta1::GuaranteedFixedPriceTerms.new(
          guaranteed_looks: 1, guaranteed_impressions: 1,
          fixed_prices: [fixed_price], minimum_daily_looks: 1))

  deal_terms = Google::Apis::Adexchangebuyer2V2beta1::DealTerms.new(
      description: 'Test deal.',
      branding_type: 'BRANDED',
      seller_time_zone: 'America/New_York',
      estimated_gross_spend: estimated_gross_spend,
      estimated_impressions_per_day: 1,
      guaranteed_fixed_price_terms: guaranteed_fixed_price_terms)

  deal = Google::Apis::Adexchangebuyer2V2beta1::Deal.new(
      display_name: 'Test Deal #%d' % rand(10000000..100000000),
      syndication_product: 'GAMES',
      deal_terms: deal_terms)

  # Append the deal to the proposal.
  new_proposal.deals = [deal]

  create_proposal(service, opts['account_id'], new_proposal)
end
