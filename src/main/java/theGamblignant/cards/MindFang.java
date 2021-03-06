package theGamblignant.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import theGamblignant.VriskaMod;
import theGamblignant.characters.TheGamblignant;
import theGamblignant.powers.LuckPower;

import static theGamblignant.VriskaMod.makeCardPath;

public class MindFang extends AbstractVriskaCard {

    public static final String ID = VriskaMod.makeID(MindFang.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheGamblignant.Enums.COLOR_COBALT;

    private static final int COST = 1;

    public MindFang() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!this.upgraded) {
            int poisonroll = roll(8, 's');
            this.addToBot(new ApplyPowerAction(m, p, new PoisonPower(m, p, poisonroll), poisonroll, AbstractGameAction.AttackEffect.POISON));
            int weakroll = roll(4, 's');
            this.addToBot(new ApplyPowerAction(m, p, new WeakPower(m, weakroll, false), weakroll, AbstractGameAction.AttackEffect.NONE));
        } else {
            int poisonroll = roll(10, 's');
            this.addToBot(new ApplyPowerAction(m, p, new PoisonPower(m, p, poisonroll), poisonroll, AbstractGameAction.AttackEffect.POISON));
            int weakroll = roll(5, 's');
            this.addToBot(new ApplyPowerAction(m, p, new WeakPower(m, weakroll, false), weakroll, AbstractGameAction.AttackEffect.NONE));
        }
        this.addToBot(new ApplyPowerAction(p, p, new LuckPower(p, -1), -1));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
