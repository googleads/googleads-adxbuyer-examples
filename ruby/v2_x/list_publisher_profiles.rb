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
# This example lists Marketplace publisher profiles.
#
# To get Account IDs, run list_accounts.rb.
#
# Tags: Accounts.PublisherProfiles.list

require 'optparse'

require_relative '../samples_util'


def list_publisher_profiles(ad_exchange_buyer, account_id)
  begin
    response = ad_exchange_buyer.list_account_publisher_profiles(
      account_id)

    unless response.publisher_profiles.nil?
      puts 'Found the following publisher profiles:'
      response.publisher_profiles.each do |publisher_profile|
        puts '* Publisher Profile ID: %s'\
            % publisher_profile.publisher_profile_id
        puts "\tSeller account ID: %s" % publisher_profile.seller.account_id
        puts "\tSeller sub-account ID: %s"\
            % publisher_profile.seller.sub_account_id
      end
    else
      puts 'No publisher profiles found.'
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
    )
  ]

  # Parse options.
  parser = Parser.new(options)
  opts = parser.parse(ARGV)

  list_publisher_profiles(service, opts['account_id'])
end
