#!/usr/bin/python
#
# Copyright 2016 Google Inc. All Rights Reserved.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

"""This example updates the given client buyer's status."""


import argparse
import os
import sys

sys.path.insert(0, os.path.abspath('..'))

from googleapiclient.errors import HttpError
import samples_util


DEFAULT_ACCOUNT_ID = 'ENTER_ACCOUNT_ID_HERE'
DEFAULT_CLIENT_NAME = 'ENTER_CLIENT_NAME_HERE'
DEFAULT_ENTITY_ID = 'ENTER_ENTITY_ID_HERE'
DEFAULT_ENTITY_TYPE = 'ENTER_ENTITY_TYPE_HERE'
DEFAULT_ROLE = 'ENTER_ROLE_HERE'
DEFAULT_STATUS = 'ENTER_STATUS_HERE'
DEFAULT_VISIBLE_TO_SELLER = 'ENTER_SELLER_VISIBILITY_HERE'
VALID_ENTITY_TYPES = ('ADVERTISER', 'BRAND', 'AGENCY')
VALID_ROLES = ('CLIENT_DEAL_VIEWER', 'CLIENT_DEAL_NEGOTIATOR',
               'CLIENT_DEAL_APPROVER')
VALID_STATUS = ('ACTIVE', 'DISABLED')


def main(ad_exchange_buyer, account_id, body):
  try:
    # Construct and execute the request.
    client = ad_exchange_buyer.accounts().clients().create(
        accountId=account_id, body=body).execute()
    print 'Client buyer created for account ID: "%d".' % (account_id)
    print client
  except HttpError as e:
    print e


if __name__ == '__main__':

  def entity_type(s):
    if s not in VALID_ENTITY_TYPES:
      raise argparse.ArgumentTypeError('Invalid value "%s".' % s)
    return s

  def status(s):
    if s not in VALID_STATUS:
      raise argparse.ArgumentTypeError('Invalid value "%s".' % s)
    return s

  def role(s):
    if s not in VALID_ROLES:
      raise argparse.ArgumentTypeError('Invalid value "%s".' % s)
    return s

  parser = argparse.ArgumentParser(
      description='Lists client buyers for a given Ad Exchange account id.')
  parser.add_argument(
      '-a', '--account_id', default=DEFAULT_ACCOUNT_ID, type=int,
      help=('The integer id of the Ad Exchange account.'))
  parser.add_argument(
      '-cn', '--client_name', default=DEFAULT_CLIENT_NAME,
      help=('The name used to represent this client to publishers.'))
  parser.add_argument(
      '-ei', '--entity_id', default=DEFAULT_ENTITY_ID,
      help=('The integer id representing the client entity. This is a '
            'unique id that can be found in the advertisers.txt, '
            'brands.txt, or agencies.txt dictionary files depending on the '
            'entity type. These files can be found on the following page: '
            'https://developers.google.com/ad-exchange/rtb/downloads'))
  parser.add_argument(
      '-et', '--entity_type', default=DEFAULT_ENTITY_TYPE, type=entity_type,
      help=('The type of the client entity. This can be set to any of the '
            'following: %s' % str(VALID_ENTITY_TYPES)))
  parser.add_argument(
      '-r', '--role', default=DEFAULT_ROLE, type=role,
      help=('The desired role to be assigned to the client buyer. This can '
            'be set to any of the following: %s' % str(VALID_ROLES)))
  parser.add_argument(
      '-s', '--status', default=DEFAULT_STATUS, type=status,
      help=('The desired update to the client buyer\'s status. This can be '
            'set to any of the following: %s' % str(VALID_STATUS)))
  parser.add_argument(
      '-v', '--visible_to_seller', default=DEFAULT_VISIBLE_TO_SELLER, type=bool,
      help=('Whether the client buyer will be visible to sellers.'))
  args = parser.parse_args()

  # Create a body containing the required fields.
  BODY = {
      'clientName': args.client_name,
      'entityId': args.entity_id,
      'entityType': args.entity_type,
      'role': args.role,
      'status': args.status,
      'visibleToSeller': args.visible_to_seller
  }

  try:
    service = samples_util.GetService(version='v2beta1')
  except IOError, ex:
    print 'Unable to create adexchangebuyer service - %s' % ex
    print 'Did you specify the key file in samples_util.py?'
    sys.exit()

  main(service, args.account_id, BODY)

