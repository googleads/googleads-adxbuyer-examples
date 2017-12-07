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
# This example updates the given client buyer's status.
#
# To get Account IDs, run list_accounts.rb.
#
# Tags: Accounts.Clients.update

require 'optparse'

require_relative '../samples_util'


def update_client_buyer(
    ad_exchange_buyer, account_id, client_buyer_id, updated_client_buyer)
  begin
    client_buyer = ad_exchange_buyer.update_account_client(
      account_id, client_buyer_id, updated_client_buyer
    )

    puts 'Successfully updated client buyer for account ID %s' % account_id
    puts '* Client account ID: %s' % client_buyer.client_account_id
    puts "\tClient name: %s" % client_buyer.client_name
    puts "\tEntity ID: %s" % client_buyer.entity_id
    puts "\tEntity name: %s" % client_buyer.entity_name
    puts "\tEntity Type: %s" % client_buyer.entity_type
    puts "\tRole: %s" % client_buyer.role
    puts "\tStatus: %s" % client_buyer.status
    puts "\tVisible to seller: %s" % client_buyer.visible_to_seller
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
      'status', 'The desired update to the client buyer\'s status.',
      :short_alias => 's',
      :valid_values => ['ACTIVE', 'DISABLED'],
      :required => true,
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
                        'CLIENT_DEAL_APPROVER'],
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
      'client_buyer_id', 'The integer id of the client buyer.',
      :type => Integer, :short_alias => 'b', :required => true,
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

  updated_client_buyer = Google::Apis::Adexchangebuyer2V2beta1::Client.new(
      visible_to_seller: opts['visible_to_seller'],
      status: opts['status'],
      entity_type: opts['entity_type'],
      role: opts['role'],
      client_name: opts['client_name'],
      client_account_id: opts['client_buyer_id'],
      entity_id: opts['entity_id']
  )

  update_client_buyer(
      service, opts['account_id'], opts['client_buyer_id'],
      updated_client_buyer
  )
end
