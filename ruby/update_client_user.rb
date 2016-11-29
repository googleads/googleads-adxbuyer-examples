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
# This example updates the given client user's status.
#
# To get Account IDs, run list_accounts.rb.
#
# Tags: Accounts.Clients.update

require 'optparse'

require_relative 'samples_util'


def update_client_user(ad_exchange_buyer, account_id, client_buyer_id,
    client_user_id, updated_client_user)
  begin
    client_user = ad_exchange_buyer.update_account_client_user(
      account_id, client_buyer_id, client_user_id, updated_client_user
    )

    puts ('Successfully updated client user for account ID %s / client ' +
         'buyer ID %s') % [account_id, client_buyer_id]
    puts '* Client user ID: %s' % client_user.user_id
    puts "\tClient account ID: %d" % client_user.client_account_id
    puts "\tEmail: %s" % client_user.email
    puts "\tStatus: %s" % client_user.status
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
    exit
  rescue Signet::AuthorizationError => e
    puts e.message
    puts 'Did you specify the key file in samples_util.rb'
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
      'client_buyer_id', 'The integer id of the client buyer.',
      :type => Integer, :short_alias => 'b', :required => true,
      :default_value => nil  # Insert default value here.
    ),
    Option.new(
      'user_id', 'The integer id of the client user.',
      :type => Integer, :short_alias => 'u', :required => true,
      :default_value => nil  # Insert default value here.
    ),
    Option.new(
      'status', 'The desired update to the client buyer\'s status.',
      :short_alias => 's',
      :valid_values => ['ACTIVE', 'DISABLED'],
      :required => true,
      :default_value => nil  # Insert default value here.
    )
  ]

  # Parse options.
  parser = Parser.new(options)
  opts = parser.parse(ARGV)

  updated_client_user = Google::Apis::Adexchangebuyer2V2beta1::ClientUser.new
  updated_client_user.status = opts['status']

  update_client_user(service, opts['account_id'], opts['client_buyer_id'],
      opts['user_id'], updated_client_user)
end
