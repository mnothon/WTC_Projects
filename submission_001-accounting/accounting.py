
from user.authentication import authenticate_user
from transactions.journal import receive_income, pay_expense
from sys import argv
import banking

def run_accounting_app():
    for i in range(1, len(argv)):
        print(argv[i])
    amount = '100'
    authenticate_user()
    receive_income(amount)
    pay_expense(amount)
    banking.fvb_recon()  

if __name__ == "__main__":
    run_accounting_app()