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
# Lists the client buyers for a given account.
#
# To get Account IDs, run list_accounts.rb.
#
# Tags: Accounts.Clients.list

require 'optparse'

require_relative '../samples_util'


def list_client_buyers(ad_exchange_buyer, account_id, page_size)
  begin
    client_buyers = ad_exchange_buyer.list_account_clients(
      account_id, page_size: page_size
    )

    if !client_buyers.clients.nil?
      puts 'Found the following client buyers for account ID %s:' % account_id
      client_buyers.clients.each do |client_buyer|
        puts '* Client account ID: %s' % client_buyer.client_account_id
        puts "\tClient name: %s" % client_buyer.client_name
        puts "\tEntity ID: %s" % client_buyer.entity_id
        puts "\tEntity name: %s" % client_buyer.entity_name
        puts "\tEntity Type: %s" % client_buyer.entity_type
        puts "\tRole: %s" % client_buyer.role
        puts "\tStatus: %s" % client_buyer.status
        puts "\tVisible to seller: %s" % client_buyer.visible_to_seller
      end
    else
      puts 'No client buyers found for account ID %s.' % account_id
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
      'account_id', 'The integer ID of the Ad Exchange buyer account.',
      :type => Integer, :short_alias => 'a', :required => true,
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

  list_client_buyers(service, opts['account_id'], opts['page_size'])
end
