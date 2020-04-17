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

"""This example sends out an invitation for a given client buyer."""


import argparse
import pprint
import os
import sys

sys.path.insert(0, os.path.abspath('..'))

from googleapiclient.errors import HttpError
import samples_util


DEFAULT_ACCOUNT_ID = 'ENTER_ACCOUNT_ID_HERE'
DEFAULT_CLIENT_BUYER_ID = 'ENTER_CLIENT_BUYER_ID_HERE'
DEFAULT_EMAIL = 'INSERT_EMAIL_HERE'


def main(ad_exchange_buyer, account_id, client_account_id, body):
  try:
    invitation = ad_exchange_buyer.accounts().clients().invitations().create(
        accountId=account_id, clientAccountId=client_account_id,
        body=body).execute()
    print(f'Invitation was sent for Account ID "{account_id}" and Client '
          f'Account Id: {client_account_id} to {body["email"]}')
    pprint.pprint(invitation)
  except HttpError as e:
    print(e)


if __name__ == '__main__':
  parser = argparse.ArgumentParser(description='Sends out an invitation for '
                                               'a given client buyer.')
  parser.add_argument(
      '-a', '--account_id', default=DEFAULT_ACCOUNT_ID, type=int,
      help=('The integer id of the Authorized Buyers account.'))
  parser.add_argument(
      '-c', '--client_buyer_id', default=DEFAULT_CLIENT_BUYER_ID, type=int,
      help=('The integer id of the client buyer.'))
  parser.add_argument(
      '-e', '--email', default=DEFAULT_EMAIL,
      help=('The email that the invitation will be sent to.'))
  args = parser.parse_args()

  BODY = {
      'email': args.email
  }

  try:
    service = samples_util.GetService('v2beta1')
  except IOError as ex:
    print(f'Unable to create adexchangebuyer service - {ex}')
    print('Did you specify the key file in samples_util.py?')
    sys.exit(1)

  main(service, args.account_id, args.client_buyer_id, BODY)

