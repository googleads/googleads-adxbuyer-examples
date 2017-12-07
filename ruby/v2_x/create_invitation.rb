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
# This example sends out an invitation for a given client buyer.
#
# Tags: Accounts.Clients.Invitations.create

require 'optparse'

require_relative '../samples_util'


def create_invitation(
    ad_exchange_buyer, account_id, client_buyer_id, new_invitation)
  begin
    invitation = ad_exchange_buyer.create_account_client_invitation(
      account_id, client_buyer_id, new_invitation
    )

    puts 'New invitation sent by account ID %s / client buyer ID %s:'\
         % [account_id, client_buyer_id]
    puts '* Invitation ID: %s' % invitation.invitation_id
    puts "\tClient account ID: %s" % invitation.client_account_id
    puts "\tEmail: %s" % invitation.email
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
      'client_buyer_id', 'The integer id of the client buyer.',
      :type => Integer, :short_alias => 'b', :required => true,
      :default_value => nil  # Insert default value here.
    ),
    Option.new(
      'email', 'The email that the invitation will be sent to.',
      :short_alias => 'e', :required => true,
      :default_value => nil  # Insert default value here.
    )
  ]

  # Parse options.
  parser = Parser.new(options)
  opts = parser.parse(ARGV)

  new_invitation = (
      Google::Apis::Adexchangebuyer2V2beta1::ClientUserInvitation.new(
          email: opts['email']
      )
  )

  create_invitation(
      service, opts['account_id'], opts['client_buyer_id'], new_invitation
  )
end
