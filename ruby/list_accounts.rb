#!/usr/bin/env ruby
# Encoding: utf-8
#
# Copyright:: Copyright 2015, Google Inc. All Rights Reserved.
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
# Retrieves a list of your Accounts.
#
# Accounts.list

require_relative './samples_util'


def list_accounts(ad_exchange_buyer)
  begin
    accounts_list = ad_exchange_buyer.list_accounts()

    if accounts_list.items.any?
      puts 'Found the following DoubleClick Ad Exchange Buyer Accounts:'
      accounts_list.items.each do |account|
        puts 'AccountID: %d' % account.id
        puts "\tCookie matching nid: %s" % account.cookie_matching_nid
        puts "\tCookie matching URL: %s" % account.cookie_matching_url
        puts "\tMaximum active creatives: %d" % account.maximum_active_creatives
        puts "\tMaximum total QPS: %d" % account.maximum_total_qps
        puts "\tNumber active creatives: %d" % account.number_active_creatives
        puts "\tBidder Locations:"
        account.bidder_location.each do |bidder_location|
          puts "\t\tURL: %s" % bidder_location.url
          puts "\t\t\tRegion: %s" % bidder_location.region
          puts "\t\t\tBid Protocol: %s" % bidder_location.bid_protocol
          puts "\t\t\tMaximum QPS: %s" % bidder_location.maximum_qps
        end
      end
    else
      puts 'No DoubleClick Ad Exchange Buyer Accounts were found.'
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
    service = get_service()
  rescue ArgumentError => e
    puts 'Unable to create service: %s' % e.message
    exit
  rescue Signet::AuthorizationError => e
    puts e.message
    puts 'Did you specify the key file in samples_util.rb?'
    exit
  end
  list_accounts(service)
end
