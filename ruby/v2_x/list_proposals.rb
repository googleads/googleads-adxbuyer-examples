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
# This example lists Marketplace proposals available to a given accountId.
#
# By default, this example will list all proposals accessible to the specified
# Account. You can use the --filter argument if you would like to provide a
# more complex filter.
#
# To get Account IDs, run list_accounts.rb.
#
# Tags: Accounts.Proposals.list

require 'optparse'

require_relative '../samples_util'


def list_proposals(
    ad_exchange_buyer, account_id, filter, filter_syntax)
  begin
    response = ad_exchange_buyer.list_account_proposals(
      account_id, filter: filter, filter_syntax: filter_syntax
    )

    unless response.proposals.nil?
      puts 'Found the following proposals:'
      response.proposals.each do |proposal|
        puts '* Proposal ID: %s' % proposal.proposal_id
        puts "\tBuyer account ID: %s" % proposal.buyer.account_id
        puts "\tSeller account ID: %s" % proposal.seller.account_id
        puts "\tSeller sub-account ID: %s" % proposal.seller.sub_account_id
        puts "\tProposal state: %s" % proposal.proposal_state
        puts "\tProposal revision: %d" % proposal.proposal_revision
      end
    else
      puts 'No proposals found.'
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
      :type => Integer, :short_alias => 'a', :required => true,
      :default_value => nil  # Insert default value here.
    ),
    Option.new(
      'filter', 'Optional filter used to filter the Proposals.',
      :type => String, :short_alias => 'f', :required => false,
      :default_value => ''  # Insert default value here.
    ),
    Option.new(
      'filter_syntax',
      ('Optional Enum value specifying whether the given filter\'s syntax '
       'is PQL or LIST_FILTER. PQL is the default syntax used if none is '
       'specified; however, this example will default to LIST_FILTER.'),
      :type => String, :short_alias => 's', :required => false,
      :default_value => 'LIST_FILTER'  # Insert default value here.
    ),
  ]

  # Parse options.
  parser = Parser.new(options)
  opts = parser.parse(ARGV)

  list_proposals(
      service, opts['account_id'], opts['filter'], opts['filter_syntax']
  )
end
