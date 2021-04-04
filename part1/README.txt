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

# Γραμματική LL(1)
exp -> term exp2
exp2 -> + term exp2
      | - term exp2
      | ε
term -> factor term2
term2 -> ** term
       | ε
factor -> ( exp )
        | num
num -> 0..9 digit
digit -> num
       | ε

# FIRST sets
FIRST(exp) = FIRST(term) = FIRST(factor) = {'('} U FIRST(num) = {'(', '0'..'9'}
FIRST(exp2) = {'+', '-', ε}
FIRST(term2) = {"**", ε}
FIRST(num) = {'0'..'9'}
FIRST(digit) = FIRST(num) U {ε} = {'0'..'9', ε}

# FOLLOW sets
FOLLOW(exp) = FOLLOW(exp2) U {')'} = {')', $}
FOLLOW(term) = FIRST(exp2) U FOLLOW(exp) = {'+', '-', ')', $}            # Because ε inside FIRST(exp2)
FOLLOW(term2) = FOLLOW(term) = {'+', '-', ')' ,$}
FOLLOW(factor) = FIRST(term2) U FOLLOW(term) = {'+', '-', "**", ')', $}  # Because ε inside FIRST(term2)
FOLLOW(num) = FOLLOW(factor) = {'+', '-', "**", ')', $}
FOLLOW(digit) = FOLLOW(num) = {'+', '-', "**", ')', $}

# FIRST+ sets that we card about (where there is a choice to be made between rules)
FIRST+(exp2 -> + term exp2) = {'+'}
FIRST+(exp2 -> - term exp2) = {'-'}
FIRST+(exp2 -> ε) = FIRST(ε) + FOLLOW(exp2) = {')', $}

FIRST+(term2 -> ** factor term2) = {"**"}
FIRST+(term2 -> ε) = FIRST(ε) + FOLLOW(term2) = {'+', '-', ')' ,$}

FIRST+(factor -> ( exp )) = {'('}
FIRST+(factor -> num) = {'0'..'9'}

FIRST+(digit -> num) = FIRST(num) = {'0'...'9'}
FIRST+(digit -> ε) = FIRST(ε) + FOLLOW(digit) = {'+', '-', "**", ')', $}

Για δοκιμή:
$ make
$ java Main

Σημείωση: Για να δουλέψει ο parser πρέπει να εισάγουμε την έκφραση, και να
πατήσουμε Enter. Δυστυχώς η InputStream.read() στο πρώτο Ctrl-D μπλοκάρει
περιμένοντας input, αντί να επιστρέψει -1. Αν πατήσουμε και δεύτερη φορά Ctrl-D
ή το Enter, δουλεύει κανονικά επιστρέφοντας -1.

Παρόλα ταύτα απο άποψη sh scripting δουλεύουν και τα 2 σωστά:
* echo "2+3" | java Main (Εισάγει '\n' πριν το τέλος)
* printf "2+3" | java Main (Ατόφια η έκφραση χωρίς '\n')

Υπάρχουν commented out, κατατοπιστικά println's
