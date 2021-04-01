Ο parser στο συγκεκριμένο μέρος της εργασίας αποτελεί κομπιουτεράκι που
αξιολογεί συμβολοσειρές της παρακάτω γραμματικής:

exp -> num
     | exp op exp
     | (exp)
op -> +
    | -
    | **
num -> digit
     | digit num
digit -> 0
       | 1
       | 2
       | 3
       | 4
       | 5
       | 6
       | 7
       | 8
       | 9

Παράδειγμα μιας τέτοιας συμβολοσειράς είναι η 13+(23-2)+3**2

Για να μπορέσουμε να υλοποιήσουμε LL(1) parser πρέπει να προσαρμόσουμε τη
δοθείσα γραμματική αφαιρώντας την αριστερή αναδρομή (left recursion). Επίσης
πρέπει να φροντίσουμε να τηρείται η προτεραιότητα των τελεστών:

exp -> term exp2
exp2 -> + term exp2
      | - term exp2
      | ε
term -> factor term2
term2 -> ** factor term2
       | ε
factor -> ( exp )
        | num
num -> digit
     | digit num
digit -> 0
       | 1
       | 2
       | 3
       | 4
       | 5
       | 6
       | 7
       | 8
       | 9

// TODO: num sets

FIRST(exp) = FIRST(term) = FIRST(factor) = {'('} U FIRST(num) = {'(', '0' ... '9'}
FIRST(exp2) = {'+', '-', ε}
FIRST(term2) = {"**", ε}
FIRST(num) = FIRST(digit) = {'0' ... '9'}


FOLLOW(exp) = FOLLOW(exp2) U {')'} = {')', $}
FOLLOW(term) = FIRST(exp2) U FOLLOW(exp) = {'+', '-', ')', $}            # Because ε inside FIRST(exp2)
FOLLOW(term2) = FOLLOW(term) = {'+', '-', ')' ,$}
FOLLOW(factor) = FIRST(term2) U FOLLOW(term) = {'+', '-', "**", ')', $}  # Because ε inside FIRST(term2)


# FIRST+ sets that we card about (where there is a choice to be made between rules)
FIRST+(exp2 -> + term exp2) = {'+'}
FIRST+(exp2 -> - term exp2) = {'-'}
FIRST+(exp2 -> ε) = FIRST(ε) + FOLLOW(exp2) = {')', $}

FIRST+(term2 -> ** factor term2) = {"**"}
FIRST+(term2 -> ε) = FIRST(ε) + FOLLOW(term2) = {'+', '-', ')' ,$}

FIRST+(factor -> ( exp )) = {'('}
FIRST+(factor -> num) = {'0' ... '9'}


Για δοκιμή:
$ make
$ java Main
