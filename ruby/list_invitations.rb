#!/usr/bin/env ruby
# Encoding: utf-8
#
# Copyright:: Copyright 2016, Google Inc. All Rights Reserved.
#
# License:: Licensed under the Apache License, Version 2.0 (the "License");
#           you may not use this file except in compliance with the License.
#           You may obtain a copy of the License at
#
#           http://www.apache.org/licenses/LICENSE-2.0
#
#           Unless required by applicable law or agreed to in writing, software
#           distributed under the License is distributed on an "AS IS" BASIS,
#           WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
#           implied.
#           See the License for the specific language governing permissions and
#           limitations under the License.
#
# This example lists the invitations sent out for a given client buyer.
#
# To get Account IDs, run list_accounts.rb.
# To get client buyer IDs, run list_client_buyers.rb.
#
# Tags: Accounts.Clients.Invitations.list

require 'optparse'

require_relative 'samples_util'


def list_invitations(ad_exchange_buyer, account_id, client_buyer_id, page_size)
  begin
    invitations = ad_exchange_buyer.list_account_client_invitations(
      account_id, client_buyer_id, page_size: page_size
    )

    if !invitations.invitations.nil?
      puts ('Found the following invitations for account ID %s / client buyer'
           ' ID %s' % [account_id, client_buyer_id])
      invitations.invitations.each do |invitation|
        puts '* Invitation ID: %d' % invitation.invitation_id
        puts "\tClient account ID: %d" % invitation.client_account_id
        puts "\tEmail: %s" % invitation.email
      end
    else
      puts 'No invitations found for account ID %s / client buyer ID %s.'\
           % [account_id, client_buyer_id]
    end

  rescue Google::Apis::ServerError => e
    puts "The following server error occured:\n%s" % e.message
  rescue Google::Apis::ClientError => e
    puts "Invalid client request:\n%s" % e.message
  rescue Google::Apis::AuthorizationError => e
    puts "Authorization error occured:\n%s" % e.message
  end
end


if __FILE__ == $0
  begin
    # Retrieve the service used to make API requests.
    service = get_service(version=ADEXCHANGEBUYER_V2BETA1)
  rescue ArgumentError => e
    puts 'Unable to create service: %s' % e.message
    puts 'Did you specify the key file in util.rb?'
    exit
  rescue Signet::AuthorizationError => e
    puts e.message
    puts 'Did you set the correct Service Account Email in samples_util.rb?'
    exit
  end

  # Set options and default values for fields used in this example.
  options = [
    Option.new(
      'account_id', 'The integer ID of the Ad Exchange buyer account.',
      :type => Integer, :short_alias => 'a', :required => true,
      :default_value => nil  # Insert default value here.
    ),
    Option.new(
      'client_buyer_id', 'The integer ID of the client buyer.',
      :type => Integer, :short_alias => 'b', :required => true,
      :default_value => nil  # Insert default value here.
    ),
    Option.new(
      'max_page_size',
      'The maximum number of entries returned on one result page.',
      :type => Integer, :short_alias => 'm', :required => true,
      :default_value => MAX_PAGE_SIZE
    )
  ]

  # Parse options.
  parser = Parser.new(options)
  opts = parser.parse(ARGV)

  list_invitations(service, opts['account_id'], opts['client_buyer_id'],
      opts['max_page_size']
  )
end
