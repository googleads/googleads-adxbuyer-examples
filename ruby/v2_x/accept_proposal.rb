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
# This example accepts the given proposal at the given revision.
#
# Tags: Accounts.Proposals.accept

require 'optparse'

require_relative '../samples_util'


def accept_proposal(ad_exchange_buyer, account_id, proposal_id, body)
  begin
    proposal = ad_exchange_buyer.accept_proposal(
      account_id, proposal_id, body
    )

    puts 'Successfully accepted proposal for buyer account ID %s' % account_id
    puts "\tProposal ID: %s" % proposal_id
    puts "\tBuyer account ID: %s" % proposal.buyer.account_id
    puts "\tSeller account ID: %s" % proposal.seller.account_id
    puts "\tSeller sub-account ID: %s" % proposal.seller.sub_account_id
    puts "\tProposal state: %s" % proposal.proposal_state
    puts "\tProposal revision: %d" % proposal.proposal_revision
    puts "\tDeals:"
    proposal.deals.each do |deal|
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
      'proposal_id', 'The ID of the Proposal to be accepted.',
      :type => String, :short_alias => 'i', :required => true,
      :default_value => nil  # Insert default value here.
    ),
    Option.new(
      'proposal_revision',
      'The integer revision of the proposal being accepted.',
      :type => Integer, :short_alias => 'r', :required => true,
      :default_value => nil  # Insert default value here.
    )
  ]

  # Parse options.
  parser = Parser.new(options)
  opts = parser.parse(ARGV)

  body = Google::Apis::Adexchangebuyer2V2beta1::AcceptProposalRequest.new(
      proposal_revision: opts['proposal_revision'])

  accept_proposal(
      service, opts['account_id'], opts['proposal_id'], body)
end
