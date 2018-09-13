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
# Lists out the contents of a Performance Report.
#
# To get Account IDs, run list_accounts.rb.
#
# Tags: PerformanceReport.list

require 'optparse'

require_relative '../samples_util'


def list_performance_report(
    ad_exchange_buyer, account_id, end_date_time, start_date_time, max_results)
  begin
    performance_report_list = ad_exchange_buyer.list_performance_reports(
      account_id, end_date_time, start_date_time, max_results: max_results
    )

    puts 'Successfully Retrieved the report:'
    performance_report_list.performance_report.each do |performance_report|
      puts '* Performance Report'
      puts "\tBid rate: %s" % performance_report.bid_rate
      puts "\tBid request rate: %s" % performance_report.bid_request_rate
      puts "\tCallout status rate: %s" % performance_report.callout_status_rate
      puts "\tCookie matcher status rate: %s" %
          performance_report.cookie_matcher_status_rate
      puts "\tCreative status rate: %s" %
          performance_report.creative_status_rate
      puts "\tFiltered bid rate: %s" % performance_report.filtered_bid_rate
      puts "\tHosted match status rate: %s" %
          performance_report.hosted_match_status_rate
      puts "\tInventory match rate: %s" %
          performance_report.inventory_match_rate
      puts "\tLatency 50th percentile: %s" %
          performance_report.latency_50th_percentile
      puts "\tLatency 85th percentile: %s" %
          performance_report.latency_85th_percentile
      puts "\tLatency 95th percentile: %s" %
          performance_report.latency_95th_percentile
      puts "\tNo quota in region: %s" % performance_report.no_quota_in_region
      puts "\tOut of quota: %s" % performance_report.out_of_quota
      puts "\tPixel match requests: %s" %
          performance_report.pixel_match_requests
      puts "\tPixel match responses: %s" %
          performance_report.pixel_match_responses
      puts "\tQuota configured limit: %s" %
          performance_report.quota_configured_limit
      puts "\tQuota throttled limit: %s" %
          performance_report.quota_throttled_limit
      puts "\tRegion: %s" % performance_report.region
      puts "\tSuccessful request rate: %s" %
          performance_report.successful_request_rate
      puts "\tTimestamp: %s" % performance_report.timestamp
      puts "\tUnsuccessful request rate: %s" %
          performance_report.unsuccessful_request_rate
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
      'end_date_time',
      ('The end time of the report in ISO 8601 timestamp format using ' +
      'UTC. (YYYY-MM-DD)'
      ),
      :short_alias => 'e', :required => true,
      :default_value => nil  # Insert default value here.
    ),
    Option.new(
      'start_date_time',
      ('The start time of the report in ISO 8601 timestamp format using ' +
      'UTC. (YYYY-MM-DD)'
      ),
      :short_alias => 's', :required => true,
      :default_value => nil  # Insert default value here.
    ),
    Option.new(
      'max_results',
      'The maximum number of entries returned on one result page.',
      :type => Integer, :short_alias => 'm', :required => true,
      :default_value => MAX_PAGE_SIZE  # Insert default value here.
    )
  ]

  # Parse options.
  parser = Parser.new(options)
  opts = parser.parse(ARGV)

  list_performance_report(
      service, opts['account_id'], opts['end_date_time'],
      opts['start_date_time'], opts['max_results']
  )
end
