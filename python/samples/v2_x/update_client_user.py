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

"""This example updates the given client user's status."""


import argparse
import pprint
import os
import sys

sys.path.insert(0, os.path.abspath('..'))

from googleapiclient.errors import HttpError
import samples_util


DEFAULT_ACCOUNT_ID = 'ENTER_ACCOUNT_ID_HERE'
DEFAULT_CLIENT_BUYER_ID = 'ENTER_CLIENT_BUYER_ID_HERE'
DEFAULT_USER_ID = 'ENTER_USER_ID_HERE'
VALID_STATUS = ('ACTIVE', 'DISABLED')


def main(ad_exchange_buyer, account_id, client_account_id, user_id, body):
  try:
    user = ad_exchange_buyer.accounts().clients().users().update(
        accountId=account_id, clientAccountId=client_account_id,
        userId=user_id, body=body).execute()
    print(f'User for Account ID "{account_id}" and Client Account Id: '
          f'"{client_account_id}" has been updated.')
    pprint.pprint(user)
  except HttpError as e:
    print(e)


if __name__ == '__main__':

  def status(s):
    if s not in VALID_STATUS:
      raise argparse.ArgumentTypeError('Invalid value "%s".' % s)
    return s

  # Optional arguments; overrides default values if set.
  parser = argparse.ArgumentParser(description='Updates the status of a '
                                               'client user.')
  parser.add_argument(
      '-a', '--account_id', default=DEFAULT_ACCOUNT_ID, type=int,
      help='The integer id of the Authorized Buyers account.')
  parser.add_argument(
      '-c', '--client_buyer_id', default=DEFAULT_CLIENT_BUYER_ID, type=int,
      help='The integer id of the client buyer.')
  parser.add_argument(
      '-u', '--user_id', default=DEFAULT_USER_ID, type=int,
      help='The integer id of the client user.')
  parser.add_argument(
      '-s', '--status', default='DISABLED', type=status,
      help=('The desired update to the client user\'s status. This can be '
            'set to any of the following: %s' % str(VALID_STATUS)))
  args = parser.parse_args()
  # Create a body containing the fields to be updated.
  # For this resource, only the "status" field can be modified.
  BODY = {'status': args.status}

  try:
    service = samples_util.GetService('v2beta1')
  except IOError as ex:
    print(f'Unable to create adexchangebuyer service - {ex}')
    print('Did you specify the key file in samples_util.py?')
    sys.exit(1)

  main(service, args.account_id, args.client_buyer_id, args.user_id, BODY)

