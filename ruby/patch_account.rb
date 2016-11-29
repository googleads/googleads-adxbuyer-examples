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
# Patches an account with the given cookie matching url.
#
# To get Account IDs, run list_accounts.rb.
#
# Tags: Accounts.patch

require 'optparse'

require_relative 'samples_util'


def patch_account(ad_exchange_buyer, account_id, account_patch,
    confirm_unsafe_account_change)
  begin
    account = ad_exchange_buyer.patch_account(
      account_id, account_patch,
      confirm_unsafe_account_change: confirm_unsafe_account_change
    )

    puts 'Successfully patched Account:'
    puts "\tAccountID: %d" % account.id
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
    service = get_service()
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
      'account_id', 'The integer ID of the account you\'re updating.',
      :type => Integer, :short_alias => 'a', :required => true,
      :default_value => nil  # Insert default value here.
    ),
    Option.new(
      'cookie_matching_url', 'The base URL used in cookie match requests.',
      :short_alias => 'c', :required => false,
      :default_value => nil  # Insert default value here.
    ),
    Option.new(
      'confirm_unsafe_change',
      'Confirmation for erasing bidder and cookie matching URLs.',
      :type => FalseClass, :short_alias => 'u', :required => true,
      :default_value => false  # Insert default value here.
    )
  ]

  # Parse options.
  parser = Parser.new(options)
  opts = parser.parse(ARGV)

  account_patch = Google::Apis::AdexchangebuyerV1_4::Account.new
  account_patch.cookie_matching_url = opts['cookie_matching_url']

  patch_account(service, opts['account_id'], account_patch,
      opts['confirm_unsafe_change']
  )
end
