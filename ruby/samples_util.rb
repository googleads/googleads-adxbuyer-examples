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
# Common utilities used by the Authorized Buyers Ad Exchange Buyer API Samples.

require 'optparse'
require 'google/apis/adexchangebuyer_v1_3'
require 'google/apis/adexchangebuyer_v1_4'
require 'google/apis/adexchangebuyer2_v2beta1'
require 'google/apis/options'
require 'googleauth/service_account'

ADEXCHANGEBUYER_V1_3 = "v1.3"
ADEXCHANGEBUYER_V1_4 = "v1.4"
ADEXCHANGEBUYER_V2BETA1 = "v2beta1"
DEFAULT_VERSION = ADEXCHANGEBUYER_V1_4
SUPPORTED_VERSIONS = [ADEXCHANGEBUYER_V1_3, ADEXCHANGEBUYER_V1_4,
    ADEXCHANGEBUYER_V2BETA1]

# The JSON key file for your Service Account found in the Google Developers
# Console.
KEY_FILE = 'path_to_key'  # Path to JSON file containing your private key.

# The maximum number of results to be returned in a page for any list response.
MAX_PAGE_SIZE = 50

# Handles authentication and initializes the client.
def get_service(version=DEFAULT_VERSION)
  if !SUPPORTED_VERSIONS.include? version
    raise ArgumentError, (
      'Unsupported version of the Ad Exchange Buyer API specified!'
    )
  end

  Google::Apis::ClientOptions.default.application_name =
      "Ruby Ad Exchange Buyer samples: #{$0}"
  Google::Apis::ClientOptions.default.application_version = "1.0.0"

  if version == ADEXCHANGEBUYER_V1_3
    service = Google::Apis::AdexchangebuyerV1_3::AdExchangeBuyerService.new
  elsif version == ADEXCHANGEBUYER_V1_4
    service = Google::Apis::AdexchangebuyerV1_4::AdExchangeBuyerService.new
  elsif version == ADEXCHANGEBUYER_V2BETA1
    service =
      Google::Apis::Adexchangebuyer2V2beta1::AdExchangeBuyerIIService.new
  end

  auth_options = {
    :json_key_io => File.open(KEY_FILE, "r"),
    :scope => "https://www.googleapis.com/auth/adexchange.buyer"
  }

  authorization = Google::Auth::ServiceAccountCredentials.make_creds(
      options=auth_options)
  service.authorization = authorization
  service.authorization.fetch_access_token!

  return service
end


# An option to be passed into the example via a command-line argument.
class Option

  # The long alias for the option; typically one or more words delimited by
  # underscores. Do not use "--help", as this is reserved for displaying help
  # information.
  attr_accessor :long_alias

  # The type used for type coercion.
  attr_accessor :type

  # The text displayed for the option if the user passes the "-h" or "--help"
  # options to display help information for the sample.
  attr_accessor :help_text

  # The short alias for the option; a single character. Do not use "-h", as
  # this is reserved for displaying help information.
  attr_accessor :short_alias

  # An optional array of values to be used to validate a user-specified value
  # against.
  attr_accessor :valid_values

  # The default value to use if the option is not specified as a command-line
  # argument.
  attr_accessor :default_value

  # A boolean indicating whether it is required that the user configure the
  # option.
  attr_accessor :required

  def initialize(long_alias, help_template, type: String,
      short_alias: nil, valid_values: nil, required: false,
      default_value: nil)

    @long_alias = long_alias

    if valid_values.nil?
      @help_text = help_template
    elsif !valid_values.kind_of?(Array)
      raise ArgumentError, 'The valid_values argument must be an Array.'
    else
      @valid_values = []
      valid_values.each do |valid_value|
        @valid_values << valid_value.upcase
      end

      @help_text = (
        help_template + " This can be set to: %s." % @valid_values.inspect
      )
    end

    @type = type
    @short_alias = short_alias
    @default_value = default_value
    @required = required
  end

  def get_option_parser_args()
    args = []

    if !short_alias.nil?
      args << '-%s %s' % [@short_alias, @long_alias.upcase]
    end

    args << '--%s %s' % [@long_alias, @long_alias.upcase]
    args << @type
    args << @help_text

    return args
  end
end


# Parses arguments for the given Options.
class Parser
  def initialize(options)
    @parsed_args = {}
    @options = options
    @opt_parser = OptionParser.new do |opts|
      options.each do |option|
        opts.on(*option.get_option_parser_args()) do |x|
          unless option.valid_values.nil?
            if option.kind_of(Array)
              x.each do |value|
                check_valid_value(option.valid_values, value)
              end
            else
              check_valid_value(option.valid_values, x)
            end
          end
          @parsed_args[option.long_alias] = x
        end
      end
    end
  end

  def check_valid_value(valid_values, value)
    unless valid_values.include?(value.upcase)
      raise 'Invalid value "%s". Valid values are: %s' %
          [value, valid_values.inspect]
    end
  end

  def parse(args)
    @opt_parser.parse!(args)

    @options.each do |option|
      if !@parsed_args.include? option.long_alias
        @parsed_args[option.long_alias] = option.default_value
      end

      if option.required and @parsed_args[option.long_alias].nil?
        raise ('You need to set "%s", it is a required field. Set it by ' +
            'passing "--%s %s" as a command line argument or giving the ' +
            'corresponding option a default value.') %
            [option.long_alias, option.long_alias, option.long_alias.upcase]
      end
    end

    return @parsed_args
  end
end
