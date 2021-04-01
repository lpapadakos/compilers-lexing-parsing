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

FIRST(exp) = FIRST(term) = FIRST(factor) = {'(', '0' ... '9'}
FIRST(num) =

Για δοκιμή:
$ make
$ java Main
