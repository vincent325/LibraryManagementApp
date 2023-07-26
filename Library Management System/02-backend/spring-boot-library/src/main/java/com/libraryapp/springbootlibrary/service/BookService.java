package com.libraryapp.springbootlibrary.service;

import com.libraryapp.springbootlibrary.dao.BookRepository;
import com.libraryapp.springbootlibrary.dao.CheckoutRepository;
import com.libraryapp.springbootlibrary.entity.Book;
import com.libraryapp.springbootlibrary.entity.Checkout;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@Transactional
public class BookService {

    private BookRepository bookRepository;

    private CheckoutRepository checkoutRepository;

    //constructor dependency injection to set up repos
    public BookService(BookRepository bookRepository,
                       CheckoutRepository checkoutRepository) {
        this.bookRepository = bookRepository;
        this.checkoutRepository = checkoutRepository;
    }


    public Book checkoutBook (String userEmail, Long bookId) throws Exception {

        Optional<Book> book = bookRepository.findById(bookId);
        //ensures you cant checkout a book multiple times, see error statement below
        Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);


        //make sure book exists, is not checked out, and is available
        if (!book.isPresent() || validateCheckout != null || book.get().getCopiesAvailable() <= 0) {
            throw new Exception("Book doesn't exist or already checked out by user");
        }


        //like a hashmap, decrement once it is checked out using getter/setter
        book.get().setCopiesAvailable(book.get().getCopiesAvailable() - 1);
        bookRepository.save(book.get());


        //new record of checkout
        Checkout checkout = new Checkout(
                userEmail,
                LocalDate.now().toString(),
                LocalDate.now().plusDays(7).toString(),
                book.get().getId()
        );
        //save into database
        checkoutRepository.save(checkout);
        //return the book
        return book.get();
    }

    public Boolean checkoutBookByUser(String userEmail, Long bookId) {
        Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);
        if (validateCheckout != null) {
            return true;
        } else {
            return false;
        }
    }

    //the method from checkout repo returns a list of all checkouts
    //this returns the size
    public int currentLoansCount(String userEmail) {
        return checkoutRepository.findBooksByUserEmail(userEmail).size();
    }
}
