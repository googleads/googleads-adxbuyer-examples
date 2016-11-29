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
# Creates a new client buyer for the given account.
#
# To get Account IDs, run list_accounts.rb.
#
# Tags: Accounts.Clients.create

require 'optparse'

require_relative 'samples_util'


def create_client_buyer(ad_exchange_buyer, account_id, new_client_buyer)
  begin
    client_buyer = ad_exchange_buyer.create_account_client(
        account_id, new_client_buyer
    )

    puts 'New client buyer added successfully for account ID %s:' % account_id
    puts '* Client account ID: %s' % client_buyer.client_account_id
    puts "\tClient name: %s" % client_buyer.client_name
    puts "\tEntity ID: %s" % client_buyer.entity_id
    puts "\tEntity name: %s" % client_buyer.entity_name
    puts "\tEntity Type: %s" % client_buyer.entity_type
    puts "\tRole: %s" % client_buyer.role
    puts "\tStatus: %s" % client_buyer.status
    puts "\tVisible to seller: %s" % client_buyer.visible_to_seller
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
    # Retrieve service used to make API requests.
    service = get_service(version=ADEXCHANGEBUYER_V2BETA1)
  rescue ArgumentError => e
    puts 'Unable to create service: %s' % e.message
    exit
  rescue Signet::AuthorizationError => e
    puts e.message
    puts 'Did you specify the key file in samples_util.rb?'
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
      'visible_to_seller',
      'Whether the client buyer will be visible to sellers.',
      :type => FalseClass, :short_alias => 'v', :required => true,
      :default_value => nil  # Insert default value here.
    ),
    Option.new(
      'entity_type', 'The type of the client entity.',
      :short_alias => 't',
      :valid_values => ['ADVERTISER', 'BRAND', 'AGENCY'],
      :required => true,
      :default_value => nil  # Insert default value here.
    ),
    Option.new(
      'role', 'The desired role to be assigned to the client buyer.',
      :short_alias => 'r',
      :valid_values => ['CLIENT_DEAL_VIEWER', 'CLIENT_DEAL_NEGOTIATOR',
          'CLIENT_DEAL_APPROVER'
      ],
      :required => true,
      :default_value => nil  # Insert default value here.
    ),
    Option.new(
      'client_name',
      'The name used to represent this client to publishers.',
      :short_alias => 'n', :required => true,
      :default_value => nil  # Insert default value here.
    ),
    Option.new(
      'entity_id',
      ('The integer id representing the client entity. This is a unique id ' +
      'that can be found in the advertisers.txt, brands.txt, or agencies.txt' +
      'dictionary files depending on the entity type. These files can be ' +
      'found on the following page: ' +
      'https://developers.google.com/ad-exchange/rtb/downloads'),
      :type => Integer, :short_alias => 'i', :required => true,
      :default_value => nil  # Insert default value here.
    )
  ]

  # Parse options.
  parser = Parser.new(options)
  opts = parser.parse(ARGV)

  new_client_buyer = Google::Apis::Adexchangebuyer2V2beta1::Client.new
  new_client_buyer.visible_to_seller = opts['visible_to_seller']
  new_client_buyer.entity_type = opts['entity_type']
  new_client_buyer.role = opts['role']
  new_client_buyer.client_name = opts['client_name']
  new_client_buyer.entity_id = opts['entity_id']

  create_client_buyer(service, opts['account_id'], new_client_buyer)
end
