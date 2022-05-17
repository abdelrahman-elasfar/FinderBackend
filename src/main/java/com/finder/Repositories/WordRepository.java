
package com.finder.prod.Repositories;

import com.finder.prod.Models.Word;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface WordRepository extends CrudRepository<Word, String> {}