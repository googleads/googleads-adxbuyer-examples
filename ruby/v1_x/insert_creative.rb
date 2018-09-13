#!/usr/bin/env ruby
# Encoding: utf-8
#
# Copyright:: Copyright 2014, Google Inc. All Rights Reserved.
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
# Inserts an HTMLSnippet Creative with the given values.
#
# To get Account IDs, run list_accounts.rb.
#
# Tags: Creatives.insert

require 'optparse'

require_relative '../samples_util'


def insert_creative(ad_exchange_buyer, new_creative)
  begin
    creative = ad_exchange_buyer.insert_creative(new_creative)

    puts 'New creative added successfully:'
    puts "\tAccount ID: %s" % creative.account_id
    puts "\tBuyer Creative ID: %s" % creative.buyer_creative_id
    puts "\tHTML Snippet: %s" % creative.html_snippet
    puts "\tAPI Upload Timestamp: %s" % creative.api_upload_timestamp
    puts "\tDeals status: %s" % creative.deals_status
    puts "\tOpen auction status: %s" % creative.open_auction_status

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
    service = get_service()
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
      'advertiser_name',
      'The name of the company being advertised by the creative.',
      :short_alias => 'n', :required => true,
      :default_value => nil  # Insert default value here.
    ),
    Option.new(
      'buyer_creative_id',
      'The ID you would like to associate with the creative being inserted.',
      :short_alias => 'b', :required => true,
      :default_value => 'RubySamples_TestCreative_%d'\
                        % rand(10000000..100000000)
    ),
    Option.new(
      'click_through_url', 'Destination URLs for the HTML snippet.',
      :type => Array, :short_alias => 'u', :required => true,
      :default_value => nil  # Insert default value here.
    ),
    Option.new(
      'html_snippet',
      'The HTML snippet that displays the ad when inserted in the web page.',
      :short_alias => 's', :required => true,
      :default_value => nil  # Insert default value here.
    ),
    Option.new(
      'height', 'The height of the creative in pixels.',
      :type => Integer, :required => true,
      :default_value => nil  # Insert default value here.
    ),
    Option.new(
      'width', 'The width of the creative in pixels.',
      :type => Integer, :required => true,
      :default_value => nil  # Insert default value here.
    )
  ]

  # Parse options.
  parser = Parser.new(options)
  opts = parser.parse(ARGV)

  new_creative = Google::Apis::AdexchangebuyerV1_4::Creative.new(
      account_id: opts['account_id'],
      advertiser_name: opts['advertiser_name'],
      buyer_creative_id: opts['buyer_creative_id'],
      click_through_url: opts['click_through_url'],
      html_snippet: opts['html_snippet'],
      height: opts['height'],
      width: opts['width']
  )

  insert_creative(service, new_creative)
end
