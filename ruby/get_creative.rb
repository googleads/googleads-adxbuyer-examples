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
# Retrieves a Creative with the given Account ID and Buyer Creative ID.

require 'optparse'

require_relative 'samples_util'


def get_creative(ad_exchange_buyer, account_id, buyer_creative_id)
  begin
    creative = ad_exchange_buyer.get_creative(account_id, buyer_creative_id)
    html_snippet = creative.html_snippet
    video_url = creative.video_url
    native_ad = creative.native_ad

    puts 'Creative Found:'
    puts "\tAccount ID: %s" % creative.account_id
    puts "\tBuyer Creative ID: %s" % creative.buyer_creative_id

    if !html_snippet.nil?
      puts "\tHTML Snippet: %s" % html_snippet
    elsif !video_url.nil?
      puts "\tVideo URL: %s" % video_url
    elsif !native_ad.nil?
      puts "\tNative Ad:"
      puts "\t\tAdvertiser: %s" % native_ad.advertiser
      puts "\t\tApp icon: %s" % native_ad.app_icon
      puts "\t\tBody: %s" % native_ad.body
      puts "\t\tCall to action: %s" % native_ad.call_to_action
      puts "\t\tClick tracking URL: %s" % native_ad.click_tracking_url
      puts "\t\tHeadline: %s" % native_ad.headline
      puts "\t\tImage: %s" % native_ad.image
      puts "\t\tImpression tracking URL: %s" % native_ad.impression_tracking_url
      puts "\t\tLogo: %s" % native_ad.logo
      puts "\t\tPrice: %s" % native_ad.price
      puts "\t\tStar rating: %s" % native_ad.star_rating
      puts "\t\tStore: %s" % native_ad.store
      puts "\t\tVideo URL: %s" % native_ad.video_url
    end

    puts "\tAPI Upload Timestamp: %s" % creative.api_upload_timestamp
    puts "\tDeals status: %s" % creative.deals_status
    puts "\tOpen auction status: %s" % creative.open_auction_status

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
      'account_id', 'The integer ID of the Ad Exchange buyer account.',
      :type => Integer, :short_alias => 'a', :required => true,
      :default_value => nil  # Insert default value here.
    ),
    Option.new(
      'buyer_creative_id',
      'The buyerCreativeId of the creative you want to retrieve.',
      :short_alias => 'b', :required => true,
      :default_value => nil  # Insert default value here.
    )
  ]

  # Parse options.
  parser = Parser.new(options)
  opts = parser.parse(ARGV)

  get_creative(service, opts['account_id'], opts['buyer_creative_id'])
end
