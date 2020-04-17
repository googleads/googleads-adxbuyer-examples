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

"""This example lists the invitations sent out for a given client buyer."""


import argparse
import pprint
import sys
import os

sys.path.insert(0, os.path.abspath('..'))

from googleapiclient.errors import HttpError
import samples_util


DEFAULT_ACCOUNT_ID = 'ENTER_ACCOUNT_ID_HERE'
DEFAULT_CLIENT_BUYER_ID = 'ENTER_CLIENT_BUYER_ID_HERE'


def main(ad_exchange_buyer, account_id, client_account_id):
  try:
    response = ad_exchange_buyer.accounts().clients().invitations().list(
        accountId=account_id, clientAccountId=client_account_id).execute()
    invitations = response['invitations']
    if invitations:
      print(f'Invitations for Account ID "{account_id}" and Client Account Id: '
            f'"{client_account_id}"')
      for invitation in invitations:
        pprint.pprint(invitation)
    else:
      print(f'No invitations for Account ID "{account_id}" and Client Account '
            f'Id: "{client_account_id}"')
  except HttpError as e:
    print(e)


if __name__ == '__main__':
  parser = argparse.ArgumentParser(description='Lists invitations sent out '
                                               'for a given client buyer.')
  parser.add_argument(
      '-a', '--account_id', default=DEFAULT_ACCOUNT_ID, type=int,
      help='The integer id of the Authorized Buyers account.')
  parser.add_argument(
      '-c', '--client_buyer_id', default=DEFAULT_CLIENT_BUYER_ID, type=int,
      help='The integer id of the client buyer.')
  args = parser.parse_args()

  try:
    service = samples_util.GetService('v2beta1')
  except IOError as ex:
    print(f'Unable to create adexchangebuyer service - {ex}')
    print('Did you specify the key file in samples_util.py?')
    sys.exit(1)

  main(service, args.account_id, args.client_buyer_id)

