package de.thb.webbaki.service;

import de.thb.webbaki.entity.Questionnaire;
import de.thb.webbaki.entity.Scenario;
import de.thb.webbaki.entity.UserScenario;
import de.thb.webbaki.repository.UserScenarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserScenarioService {
    @Autowired
    private UserScenarioRepository userScenarioRepository;

    public List<UserScenario> getAllByQuestionnaire(Questionnaire questionnaire){return userScenarioRepository.findAllByQuestionnaire(questionnaire);}

    public UserScenario getUserScenarioFromScenario(Scenario scenario){return userScenarioRepository.findByScenario(scenario);}

    public void deleteAllUserScenariosByQuestionnaireId(long id){userScenarioRepository.deleteAllByQuestionnaire_Id(id);}
    public void saveUserScenario(UserScenario userScenario){
        userScenarioRepository.save(userScenario);
    }

    public void saveAllUserScenario(List<UserScenario> userScenarios){userScenarioRepository.saveAll(userScenarios);}

    public boolean existsUerScenarioByScenarioIdAndQuestionnaireId(long scenarioId, long questId){return userScenarioRepository.existsByScenario_IdAndQuestionnaire_Id(scenarioId, questId);}
}
