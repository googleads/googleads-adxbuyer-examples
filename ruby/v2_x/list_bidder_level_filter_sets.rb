#!/usr/bin/env ruby
# Encoding: utf-8
#
# Copyright:: Copyright 2017, Google Inc. All Rights Reserved.
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
# Lists the filter sets for a given bidder.
#
# Tags: Bidders.FilterSets.list

require 'optparse'

require_relative '../samples_util'


def list_bidder_level_filter_sets(ad_exchange_buyer, owner_name, page_size)
  begin
    response = ad_exchange_buyer.list_bidder_filter_sets(
        owner_name, page_size: page_size
    )

    unless response.filter_sets.nil?
      puts 'Found the following filter sets for bidder %s:' % owner_name
      response.filter_sets.each do |filter_set|
        puts '* Filter set name: %s' % filter_set.name
        if !filter_set.absolute_date_range.nil?
          abs_date_range = filter_set.absolute_date_range
          start_date = abs_date_range.start_date
          end_date = abs_date_range.end_date
          puts "\tAbsolute date range:"
          puts "\t\tStart date: %s-%s-%s" % [start_date.year, start_date.month,
              start_date.day]
          puts "\t\tEnd date: %s-%s-%s" % [end_date.year, end_date.month,
              end_date.day]
        end
        unless filter_set.realtime_time_range.nil?
          realtime_time_range = filter_set.realtime_time_range
          puts "\tRealtime time range:"
          puts "\t\tStart timestamp: %s" % realtime_time_range.start_timestamp
        end
        unless filter_set.relative_date_range.nil?
          relative_date_range = filter_set.relative_date_range
          puts "\tRelative date range:"
          puts "\t\tOffset days: %s" % relative_date_range.offset_days
          puts "\t\tDuration days: %s" % relative_date_range.duration_days
        end
        unless filter_set.time_series_granularity.nil?
          puts "\tTime series granularity: %s" %
              filter_set.time_series_granularity
        end
        unless filter_set.format.nil?
          puts "\tFormat: %s" % filter_set.format
        end
        unless filter_set.environment.nil?
          puts "\tEnvironment: %s" % filter_set.environment
        end
        unless filter_set.platforms.nil?
          puts "\tPlatforms: %s" % filter_set.platforms.inspect
        end
        unless filter_set.seller_network_ids.nil?
          puts "\tSeller network IDs: %s" %
              filter_set.seller_network_ids.inspect
        end
      end
    else
      puts 'No filter sets found for bidder %s.' % owner_name
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
      'bidder_resource_id',
      ('The resource ID of the bidders resource for which the filter ' +
       'sets were created. This will be used to construct the ownerName ' +
       'used as a path parameter for filter set requests. For additional ' +
       'information on how to configure the ownerName path parameter, ' +
       'see: https://developers.google.com/ad-exchange/buyer-rest/' +
       'reference/rest/v2beta1/bidders.filterSets/list' +
       '#body.PATH_PARAMETERS.owner_name'),
      :short_alias => 'b', :required => true,
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

  owner_name = 'bidders/%s' % opts['bidder_resource_id']

  list_bidder_level_filter_sets(service, owner_name, opts['max_page_size'])
end
