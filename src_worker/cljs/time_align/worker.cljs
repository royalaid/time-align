(ns time-align.worker
  (:require
            [ajax.core :refer [GET POST]]
            [oops.core :refer [oget oset!]]))

(def handlers (atom {}))

(defn defhandler
  [key should-post-message? fn]
  {:pre [(keyword? key)]}
  (if-not (contains? @handlers key)
    (swap! handlers merge {key {:should-post-message should-post-message?
                                :fn fn}})))

(defn- post-message!
  [data]
  (.postMessage js/self (clj->js data)))

(defn handle-request!
  [event]
  (let [data (-> event (oget "data") (js->clj :keywordize-keys true))
        {:keys [arguments handler]} data
        handler (keyword handler)   ;;keywordiness gets lost from clj->js->clj
        handler-fn (-> @handlers handler :fn)
        should-post-message? (-> @handlers handler :should-post-message)
        result (handler-fn arguments)]
    (when should-post-message?
      (post-message! {:handled-by handler
                      :state      :success
                      :result     result}))))

(defhandler :send-analytic
            false
            (fn [{:keys [params csrf-token] :as to-post}]
              (POST "/analytics"
                    {:headers       {"Accept"       "application/transit+json"
                                     "x-csrf-token" csrf-token}
                     :params        params
                     :handler       #(post-message! {:handled-by :send-analytic
                                                     :state :success
                                                     :result  %})

                     :error-handler #(post-message! {:handled-by :send-analytic
                                                     :state :failure
                                                     :result  %})})))

(defhandler :mirror
            true
            identity)

(oset! js/self "onmessage" handle-request!)
