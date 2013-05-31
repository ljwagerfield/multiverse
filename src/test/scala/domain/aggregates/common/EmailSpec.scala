package domain.aggregates.common

import io.multiverse.domain.aggregates.common.Email
import org.specs2.mutable.Specification
import org.specs2.specification.Scope

/**
 * Email specification.
 */
class EmailSpec extends Specification {
  "email" should {
    "support basic emails" in new Scope {
      Email("joe@bloggs.com")
    }

    "support tokens" in new Scope {
      Email("joe+token@bloggs.com")
    }

    "support small TLDs" in new Scope {
      Email("joe@bloggs.co")
    }

    "support large TLDs" in new Scope {
      Email("joe@bloggs.museum")
    }

    "support IP addresses" in new Scope {
      Email("joe@[127.0.0.1]")
      Email("joe@127.0.0.1") must throwA[Exception]
    }

    "support subdomains" in new Scope {
      Email("joe@sub.bloggs.com")
    }

    "support uppercase" in new Scope {
      Email("Joe@Bloggs.com")
    }

    "support numbers" in new Scope {
      Email("joe123@bloggs456.com")
    }

    "support quoted names" in new Scope {
      Email("\"joe 123\"@bloggs.com")
      Email("\"joe 123@bloggs.com") must throwA[Exception]
      Email("joe 123\"@bloggs.com") must throwA[Exception]
    }

    "not support addresses without @" in new Scope {
      Email("joebloggs.com") must throwA[Exception]
    }

    "not support whitespace" in new Scope {
      Email("joe @bloggs.com") must throwA[Exception]
      Email("joe@ bloggs.com") must throwA[Exception]
      Email("jo e@bloggs.com") must throwA[Exception]
      Email("joe@blo ggs.com") must throwA[Exception]
      Email("joe@bloggs .com") must throwA[Exception]
      Email("joe@bloggs. com") must throwA[Exception]
      Email("joe@bloggs.com ") must throwA[Exception]
      Email(" joe@bloggs.com") must throwA[Exception]
    }
  }
}
